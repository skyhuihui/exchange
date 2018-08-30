package com.zag.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lei
 * @date 2017/2/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RedisWriteIgnore {

	enum IgnoreType{
		/**
		 * 忽略该字段,不会写入redis
		 */
		ALL,
		/**
		 * 当该字段为 null 值时,不写入 redis(不覆盖 hash)
		 */
		NULL,
		/**
		 * 当该字段为 null 或空字符串时,不写入 redis(不覆盖 hash)
		 */
		NULL_OR_EMPTY_STRING
	}

	IgnoreType type() default IgnoreType.ALL;

}
