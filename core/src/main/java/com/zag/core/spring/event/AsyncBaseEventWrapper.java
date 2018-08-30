package com.zag.core.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * 异步事件包装,配合{@link EventMulticaster}使用
 * @author stone
 * @since 2017年7月24日
 * @reviewer 
 */
final class AsyncBaseEventWrapper extends ApplicationEvent implements BaseEventWrapper {

	private static final long serialVersionUID = -7071108383289433708L;

	private final BaseEvent event;

	/**
	 * 
	 * @author stone
	 * @date 2017年7月24日
	 * @param event
	 */
	public AsyncBaseEventWrapper(BaseEvent event) {
		super(event.getSource());
		this.event = event;
	}

	public BaseEvent getEvent() {
		return event;
	}
}
