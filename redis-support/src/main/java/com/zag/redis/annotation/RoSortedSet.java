package com.zag.redis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 类注解  获取类存取的key
 * 如果有此注解 则当前进行更新保存的对象，需序列当前对象id到当前key的value
 * @author stone(by lei)
 * @date 2017年8月11日
 * @reviewer 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RoSortedSet {
	
	String key();

	String score() default "";
}
