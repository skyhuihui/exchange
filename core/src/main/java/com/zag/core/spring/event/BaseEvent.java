package com.zag.core.spring.event;

import org.springframework.context.ApplicationEvent;

public class BaseEvent extends ApplicationEvent{

	private static final long serialVersionUID = 8809937598352484211L;

	public BaseEvent(Object source) {
		super(source);
	}

}
