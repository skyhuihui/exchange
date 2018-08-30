package com.zag.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.zag.validation.MobileValidator;


@Target({FIELD,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=MobileValidator.class)
public @interface IsMobile {
	/**
	 * 验证失败会作为失败消息返回
	 * 
	 * @return
	 */
    String message() default "手机号不正确";  
    
	/**
	 * 当为false时,验证失败不会抛出异常
	 * 
	 * @return
	 */
	boolean throwable() default true;
    
    Class<?>[] groups() default {};  
     
    Class<? extends Payload>[] payload() default {}; 
    /**
     * 是否必填,如果为false,在手机号值为空的时候跳过验证
     * 
     * @return
     */
    boolean required() default true;
}
