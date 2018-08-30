package com.zag.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性注解  获取属性存取的key
 * 如果有此注解 则当前进行更新保存的对象，需增加当前对象id到当前key的value
 * @author stone(by lei)
 * @date 2017年8月11日
 * @reviewer 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FieldSortedSet {
	
	String prefix() default "";
	
	String key();

	String score() default "";
}