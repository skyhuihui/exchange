package com.zag.core.spring.event;

import com.zag.core.asserts.SystemAsserts;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 多事件监听器,子类中所有标注{@link EventHandler}注解的方法都作为一个监听对应事件的方法
 * 多级继承的情况下,只有被spring管理的那一级类中声明的事件处理方法才有效,这是防止在多个子类被spring管理时,父类的事件处理方法被调用多次
 * 
 * @author stone
 * @date 2017年7月24日
 * @see EventHandler
 * @reviewer
 */
public abstract class AbstractMultipleEventListener extends AbstractEventListener<BaseEvent> {

	private Map<Class<?>, Method> eventHandlerMapping = new HashMap<>();

	/**
	 * 如果使用xml的方式配置事件监听器子类,需要指定初始化方法为该方法
	 * @author stone
	 * @date 2017年7月24日
	 */
	@PostConstruct
	private void initMultiEventListener() {
		logger.debug("\t正在初始化多事件监听器:");
		Method[] methods = this.getClass().getDeclaredMethods();
		/*
		 * spring工具扫描到的方法包括了父类声明的方法,在监听器定义的场合这可能会导致同一个监听逻辑被同一个事件触发多次,
		 * 我们需要确保对于一个监听方法,每个对应事件只能触发一次,因此使用Class自身的方法扫描
		 */
		// Method[] methods =
		// ReflectionUtils.getAllDeclaredMethods(this.getClass());
		for (Method m : methods) {
			EventHandler handler = m.getAnnotation(EventHandler.class);
			if (handler == null)
				continue;

			Class<?>[] parameterTypes = m.getParameterTypes();
			SystemAsserts.notEmpty(parameterTypes, "多事件监听器的处理方法必须要有一个参数");
			Class<?> clz = parameterTypes[0];
			SystemAsserts.isTrue(BaseEvent.class.isAssignableFrom(clz), "多事件监听器的处理方法第一个参数必须为BaseEvent或其子类");
			if (eventHandlerMapping.containsKey(clz)) {
				SystemAsserts.isTrue(false, "事件类型%s在监听器%s中绑定了多个处理方法:%s,%s", clz.getName(), this.getClass().getName(),
						eventHandlerMapping.get(clz).getName(), m.getName());
			}
			eventHandlerMapping.put(clz, m);
			logger.debug("\t\t监听事件[{}]在方法[{}]", clz.getName(), m.getName());
		}
	}

	@Override
	protected final void handleEvent(BaseEvent event) {
		Method method = eventHandlerMapping.get(event.getClass());
		if (method != null) {
			
			logger.debug("事件{}在监听器{}中上查找到执行方法:{}", event.getClass().getSimpleName(), this.getClass().getSimpleName(), method.getName());
			ReflectionUtils.makeAccessible(method);
			ReflectionUtils.invokeMethod(method, this, event);
		}
	}

	/**
	 * BanquetExpertMultiEventListener的子类使用.
	 * 注解在方法上,方法的第一个参数必须为监听的事件对象
	 * 
	 * @author stone
	 * @date 2017年7月24日
	 * @reviewer
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	protected static @interface EventHandler {

	}
}
