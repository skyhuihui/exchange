package com.zag.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.zag.core.asserts.SystemAsserts;
import com.zag.core.spring.event.BaseEvent;
import com.zag.core.spring.event.EventPublisher;
import com.zag.core.spring.transaction.SimpleJpaTransactionManager;
import com.zag.core.util.ExecutionUnit;
import com.zag.db.redis.repository.CrudRedisDao;
import com.zag.redis.bean.BaseRedisObject;
import com.zag.support.jpa.po.BaseEntity;
import com.zag.support.jpa.po.BasePo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务基类
 *
 * @author stone
 * @since 2017年8月3日
 * @usage
 * @reviewer
 */
public abstract class AbstractBaseService {

	@Autowired
	protected IdService idService;

    @Autowired
    private EventPublisher publisher;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 立即异步发送事件
	 * @author stone
	 * @date 2017年8月15日
	 * @param event
	 */
	protected void publishEvent(BaseEvent event) {
		publisher.publishEvent(event);
	}

	/**
	 * 立即发布同步事件
	 *
	 * @param event
	 * @author stone
	 */
	protected void syncPublishEvent(BaseEvent event) {
		publisher.syncPublishEvent(event);
	}

	/**
	 * 使用事务管理器异步发送事件,事件只有在事务成功提交之后才会开始发布
	 * @author stone
	 * @date 2017年8月15日
	 * @param event
	 */
	protected void publishEventUsingTx(BaseEvent event) {
		publisher.publishEventUsingTransactionManager(event);
	}

	/**
	 * 使用事务管理器同步发送事件,事件只有在事务成功提交之后才会开始发布,本次请求在事件处理完成后才会返回
	 * @author stone
	 * @date 2017年8月15日
	 * @param event
	 */
	protected void syncPublishEventUsingTx(BaseEvent event){
		publisher.syncPublishEventUsingTransactionManager(event);
	}
	/**
	 * 使用事务管理器执行redis操作,只有事务成功提交之后才会执行这些操作,本次请求在操作结束后才会返回
	 * 类似于使用事务管理器同步发送事件
	 * @author stone
	 * @date 2017年8月15日
	 * @param unit
	 */
	protected void delayExecuteRedisOpt(ExecutionUnit unit){
		SimpleJpaTransactionManager.delayExecuteIfInTx(unit);
	}
	/**
	 * 在事务中时,在事务提交(commit)之前执行的代码<br>
	 * 		用例:		在同一个事务中,我修改并希望持久化一对RO-PO,但我不确定这个对象(主要是PO)在之后的代码中是否会被修改,<br>
	 * 				因此我将持久化RO-PO的代码放在本方法中,使持久化操作依然在事务之内,并且也达到代码后置的效果,<br>
	 * 				具体例子可以查看本方法调用链<br>
	 * @author stone
	 * @date 2017年9月1日
	 * @param unit
	 */
	protected void preCommitOpt(ExecutionUnit unit){
		SimpleJpaTransactionManager.preCommitOpt(unit);
	}

	/**
	 * 将PO持久化,并同步更新到redis
	 * 对redis的修改将在事务提交后生效,如果事务失败,则不会生效
	 * 返回PO转换成RO之后的对象
	 * @author lei
	 * @date 2017年8月19日
	 * @param repo		PO使用的JPA repository
	 * @param redisDao	RO使用的redis dao
	 * @param transfer	PO转换为RO的函数
	 * @param po		要持久化的PO对象,只能是BaseEntity的子类
	 * @return			从po转换为ro之后的ro对象
	 */
	protected <PO extends BaseEntity, RO extends BaseRedisObject<Long>> RO saveOrUpdateUsingPo(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, PO po, Function<PO, RO> transfer) {
		SystemAsserts.notNull(po.getId(),"po必须要有id");
		if (logger.isDebugEnabled()) {
			logger.debug("save-po {}", po);
		}
		final RO ro = transfer.apply(po);
		if (logger.isDebugEnabled()) {
			logger.debug("transfer-po2ro, ro={}", ro);
		}
		delayExecuteRedisOpt(() -> redisDao.save(ro));
		repo.save(po);
		return ro;
	}

	protected <PO extends BaseEntity, RO extends BaseRedisObject<Long>> void saveOrUpdateUsingPos(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, final List<PO> pos, final Function<PO, RO> transfer) {
		for (PO po : pos){
			SystemAsserts.notNull(po.getId(),"po必须要有id");
		}
		delayExecuteRedisOpt(() -> {
            List<RO> ros = pos.stream().map(transfer).collect(Collectors.toList());
            if(logger.isDebugEnabled()){
                logger.debug("POS - > ROS的结果为[{}]", JSON.toJSONString(transfer));
            }
            for (RO ro : ros) {
                redisDao.save(ro);
            }
        });
		repo.save(pos);
	}

	/**
	 * 用于保存主键自增的po,由于其id是由mysql生成,无法在事务提交之前拿到,因此本方法不再返回ro对象
	 * @author lei
	 * @date 2017年8月23日
	 * @param repo		po repository
	 * @param redisDao	ro redis dao
	 * @param po		要持久化的po对象
	 * @param transfer	将po转换为ro的函数
	 */
	protected <PO extends BasePo, RO extends BaseRedisObject<Long>> RO saveOrUpdateUsingPoPKIncreament(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, final PO po, final Function<PO, RO> transfer) {
		if (logger.isDebugEnabled()) {
			logger.debug("save-po {}", po);
		}
		final RO ro = transfer.apply(po);
		if (logger.isDebugEnabled()) {
			logger.debug("transfer-po2ro, ro={}", ro);
		}
		delayExecuteRedisOpt(new ExecutionUnit() {
			@Override
			public void execute() {

				redisDao.save(ro);
			}
		});
		repo.save(po);
		return ro;
	}

	protected <PO extends BasePo, RO extends BaseRedisObject<Long>> void batchSaveOrUpdateUsingPoPKIncreament(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, final Function<PO, RO> transfer, final List<PO> polist) {
		if (logger.isDebugEnabled()) {
			logger.debug("save-polist size={}", polist.size());
		}
		delayExecuteRedisOpt(new ExecutionUnit() {
			@Override
			public void execute() {
				//事务提交后,id被jpa写入原po
				for (PO po : polist) {
					final RO ro = transfer.apply(po);
					if (logger.isDebugEnabled()) {
						logger.debug("transfer-po2ro, ro={}", ro);
					}
					redisDao.save(ro);
				}
			}
		});
		repo.save(polist);
	}

	/**
	 * 将RO持久化,并同步更新到mysql
	 * 对redis的修改将在事务提交后生效,如果事务失败,则不会生效
	 *
	 * @author lei
	 * @date 2017年8月19日
	 * @param repo		PO使用的JPA repository
	 * @param redisDao	RO使用的redis dao
	 * @param transfer	将RO转换为PO的函数
	 * @param ro		要持久化的RO对象
	 */
	protected <PO extends BasePo, RO extends BaseRedisObject<Long>> void saveOrUpdateUsingRo(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, final RO ro, Function<RO,PO> transfer) {
		if (logger.isDebugEnabled()) {
			logger.debug("save-ro {}", ro);
		}
		PO po = transfer.apply(ro);
		if (logger.isDebugEnabled()) {
			logger.debug("transfer-ro2po, po={}", po);
		}
		delayExecuteRedisOpt(new ExecutionUnit() {
			@Override
			public void execute() {
				redisDao.save(ro);
			}
		});
		repo.save(po);
	}

	/**
	 * 根据id删除实体,同时操作redis和mysql
	 * @author lei
	 * @date 2017年8月20日
	 * @param repo		PO使用的JPA repository
	 * @param redisDao	RO使用的redis dao
	 * @param id		PO和RO的id
	 */
	protected <PO extends BasePo, RO extends BaseRedisObject<Long>> void deleteEntity(
			JpaRepository<PO, Long> repo, final CrudRedisDao<RO, Long> redisDao, Long id){
		SystemAsserts.isTrue(repo.exists(id), "id=[{}]对应的po不存在",id);
		final RO ro = redisDao.findOne(id);
		repo.delete(id);
		delayExecuteRedisOpt(new ExecutionUnit() {
			@Override
			public void execute() {
				redisDao.delete(ro);
			}
		});
	}

}
