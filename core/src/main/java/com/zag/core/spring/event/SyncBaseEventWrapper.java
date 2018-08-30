package com.zag.core.spring.event;

import org.springframework.context.ApplicationEvent;

final class SyncBaseEventWrapper extends ApplicationEvent implements BaseEventWrapper {
	private static final long serialVersionUID = -5170108322289431707L;

	private final BaseEvent event;

	/**
	 * 
	 * @author stone
	 * @date 2017年7月24日
	 * @param event
	 */
	public SyncBaseEventWrapper(BaseEvent event) {
		super(event.getSource());
		this.event = event;
	}

	@Override
	public BaseEvent getEvent() {
		return event;
	}
}
