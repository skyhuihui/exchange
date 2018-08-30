package com.zag.core.annotation;

import org.springframework.stereotype.Component;

/**
 * 子工程自行扫描的组件
 * @author stone
 * @since 2017年10月20日
 * @usage 
 * @reviewer
 */
@Component
public @interface ProjectOnly {

	String value() default "";
}
