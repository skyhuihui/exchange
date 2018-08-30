package com.zag.core.spring.transaction;

import com.zag.core.asserts.SystemAsserts;
import com.zag.core.spring.event.BaseEvent;
import com.zag.core.spring.event.EventPublisher;
import com.zag.core.util.ExecutionUnit;
import com.zag.core.util.Timers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * SimpleJpaTransactionManager
 *  JPA支持
 *
 * @author lei
 * @date 2017年10月31日
 * @reviewer
 * @see
 */
public class SimpleTransactionManager extends DataSourceTransactionManager {

    private static final long serialVersionUID = -1121090336318974597L;

    private static Logger logger = LoggerFactory.getLogger(SimpleTransactionManager.class);

    private static EventPublisher eventPublisher;

    public void setEventPublisher(EventPublisher eventPublisher) {
        SimpleTransactionManager.eventPublisher = eventPublisher;
    }


	/*
     *
	 *	*** warnning ***
	 *
	 * 	所有threadlocal都必须在事务提交时clear内容或还原默认值,否则会出现令人迷惑的错误
	 *
	 */
    /**
     * 异步事件列表
     */
    private static final ThreadLocal<List<BaseEvent>> asyncEvents = new ThreadLocal<>();
    /**
     * 同步事件列表
     */
    private static final ThreadLocal<List<BaseEvent>> syncEvents = new ThreadLocal<>();
    //预提交执行代码(在commit之前执行,接受事务管理)
    private static final ThreadLocal<List<ExecutionUnit>> preCommitOperate = new ThreadLocal<>();
    /**
     * redis后置操作是不保存上下文的:
     * 		通常在hibernate或jpa上下文中,我们save一个po多次,只会在内存中进行操作合并,然后在事务提交时进行一次持久化操作
     * 		然而redis后置是比较简陋的一种妥协设计,每次调用都会在操作队列中新增一条带操作指令
     * 		这可能引起因为顺序混乱导致数据被覆盖,请注意
     */
    /**
     * 事务提交后执行的代码, 比如redis操作
     */
    private static final ThreadLocal<List<ExecutionUnit>> postOperate = new ThreadLocal<>();
    /**
     * 事务标志
     */
    private static final ThreadLocal<Boolean> inTransactions = new ThreadLocal<>();

    public SimpleTransactionManager() {
        super();
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        super.doBegin(transaction, definition);
        inTransactions.set(true);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {
        Timers timers = Timers.createAndBegin(logger.isDebugEnabled());
        executeOpt(preCommitOperate);
        timers.record("preCommitOperate");
        super.doCommit(status);
        timers.record("transactionCommit");
        try {
            doPostCommit();
        } catch (Throwable e) {
            logger.error("post transaction execute error", e);
            e.printStackTrace();
        } finally {
            clearThreadLocal();
        }
        timers.record("postCommit");
        timers.print("use time tx-commit");
    }

    private void doPostCommit() {
        executeOpt(postOperate);
        publish(syncEvents, false);
        publish(asyncEvents, true);
        inTransactions.set(false);
    }

    private void executeOpt(ThreadLocal<List<ExecutionUnit>> unitThreadlocal) {
        List<ExecutionUnit> redisExecutionList = unitThreadlocal.get();
        if (redisExecutionList != null) {
            Timers timers = Timers.createAndBegin(logger.isDebugEnabled());
            for (ExecutionUnit unit : redisExecutionList) {
                unit.execute();
                timers.record(unit.getClass().toString());
            }
            timers.print("use time post-opt[size=" + redisExecutionList.size() + "]");
        }
    }

    private void clearThreadLocal() {
        clearList(syncEvents.get());
        clearList(asyncEvents.get());
        clearList(postOperate.get());
        clearList(preCommitOperate.get());
        inTransactions.set(false);
    }

    private void clearList(List<?> list) {
        if (list != null) {
            list.clear();
        }
    }

    private void publish(ThreadLocal<List<BaseEvent>> events, boolean asyncPublish) {
        List<BaseEvent> list = events.get();
        if (list != null && !list.isEmpty()) {
            boolean isDebugEnabled = logger.isDebugEnabled();
            for (BaseEvent event : list) {
                if (isDebugEnabled) {
                    logger.debug("applicationEventPublisher.publishEvent {}, hashcode {},ts {}", event.getClass()
                            .getCanonicalName(), event.hashCode(), event.getTimestamp());
                }
                if (asyncPublish) {
                    eventPublisher.publishEvent(event);
                } else {
                    eventPublisher.syncPublishEvent(event);
                }
            }
            list.clear();
        }
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        super.doRollback(status);
        if (logger.isDebugEnabled()) {
            logger.debug("Rollback && clear event");
        }
        clearThreadLocal();
    }

    public static void publishEvent(BaseEvent event, boolean asyncPublish) {
        SystemAsserts.notNull(eventPublisher, "事务管理器必须注入eventPublisher才能进行事件发布");
        Boolean transFlag = inTransactions.get();
        ThreadLocal<List<BaseEvent>> events = asyncPublish ? asyncEvents : syncEvents;

        if (transFlag != null && transFlag) {
            List<BaseEvent> list = events.get();
            if (list == null) {
                list = new ArrayList<>();
                events.set(list);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("thread is in Transaction send push envet to list");
            }
            list.add(event);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("thread is not in Transaction send event now! eventPublisher is : {} event is:{} ",
                        eventPublisher, event);
            }
            eventPublisher.publishEvent(event);
        }
    }

    public static void delayExecuteIfInTx(ExecutionUnit unit) {
        Boolean transFlag = inTransactions.get();
        if (transFlag != null && transFlag) {
            List<ExecutionUnit> list = postOperate.get();
            if (list == null) {
                list = new ArrayList<>(4);
                postOperate.set(list);
            }
            list.add(unit);
        } else {
            throw new UnsupportedOperationException("后置操作必须在事务中执行");
        }
    }

    public static void preCommitOpt(ExecutionUnit unit) {
        Boolean transFlag = inTransactions.get();
        if (transFlag != null && transFlag) {
            List<ExecutionUnit> list = preCommitOperate.get();
            if (list == null) {
                list = new ArrayList<>(4);
                preCommitOperate.set(list);
            }
            list.add(unit);
        } else {
            unit.execute();
        }
    }

    public static void setInTransaction(boolean intx) {
        inTransactions.set(intx);
    }

}
