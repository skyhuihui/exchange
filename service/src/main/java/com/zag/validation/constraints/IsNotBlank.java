package com.zag.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.zag.validation.NotBlankValidator;

/**
 * 替换javax.validation的非空注解,验证不通过则抛出异常
 * 
 * @author stone 2017年5月18日
 */
@Target({FIELD,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=NotBlankValidator.class)
public @interface IsNotBlank {
	/**
	 * 验证失败会作为失败消息返回
	 * 
	 * @return
	 */
    String message() default "";  
    
	/**
	 * 当为false时,验证失败不会抛出异常
	 * 
	 * @return
	 */
	boolean throwable() default true;
    
    Class<?>[] groups() default {};  
     
    Class<? extends Payload>[] payload() default {}; 
}
