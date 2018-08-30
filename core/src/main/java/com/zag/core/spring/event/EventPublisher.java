package com.zag.core.spring.event;

import com.zag.core.asserts.SystemAsserts;
import com.zag.core.exception.SystemException;
import com.zag.core.spring.transaction.SimpleJpaTransactionManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * 基础框架使用的事件发布器,实现了同时支持发布同步事件和异步事件,不推荐再单独感知spring提供的事件发布器
 * 本类需要配合事务管理器,事件广播同时使用,需要在xml中手动配置bean
 * 
 * @author stone
 * @date 2017年7月24日
 * @reviewer
 */
public class EventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher; 

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishEvent(BaseEvent event) {
		SystemAsserts.notNull(event, "发布事件时事件对象不能为null");
		applicationEventPublisher.publishEvent(new AsyncBaseEventWrapper(event));
	}
	
	public void syncPublishEvent(BaseEvent event) {
		SystemAsserts.notNull(event, "发布事件时事件对象不能为null");
		applicationEventPublisher.publishEvent(new SyncBaseEventWrapper(event));
	}


	public void publishEventUsingTransactionManager(BaseEvent event) {
		SystemAsserts.notNull(event, "发布事件时事件对象不能为null");
		try {
			SimpleJpaTransactionManager.publishEvent(event, true);
		} catch (SystemException e) {
			throw new UnsupportedOperationException("你可能没有使用SimpleTranactionManager来作为事务管理器", e);
		}
	}
	
	public void syncPublishEventUsingTransactionManager(BaseEvent event) {
		SystemAsserts.notNull(event, "发布事件时事件对象不能为null");
		try {
			SimpleJpaTransactionManager.publishEvent(event, false);
		} catch (SystemException e) {
			throw new UnsupportedOperationException("你可能没有使用SimpleTranactionManager来作为事务管理器", e);
		}
	}

}
