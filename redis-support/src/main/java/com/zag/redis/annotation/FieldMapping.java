package com.zag.redis.annotation;

import java.lang.annotation.*;

/**
 * 属性注解  获取属性存取的key
 * 如果有此注解 则当前进行更新保存的对象，需增加到当前当前key的value对象到id的映射
 * @author stone(by lei)
 * @date 2017年8月11日
 * @reviewer 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FieldMapping {

	String prefix() default "";

	String key();
}