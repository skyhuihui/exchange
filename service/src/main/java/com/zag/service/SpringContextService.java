package com.zag.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring容器工具
 * <p>
 * 该工具类需要先注册成为spring容器中的bean
 * </p>
 */
@Service
public class SpringContextService implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextService.applicationContext = applicationContext;
	}

	/**
	 * 获取Spring容器
	 *
	 * @return
	 */
	public static ApplicationContext getContext() {
		return applicationContext;
	}

	public static <T> T getBean(Class<T> beanType) {
		return applicationContext == null ? null : applicationContext.getBean(beanType);
	}

	public static Object getBean(String beanName) {
		return applicationContext == null ? null : applicationContext.getBean(beanName);
	}

	
	
}
