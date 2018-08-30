package com.zag.validation.constraints;

//import java.lang.annotation.ElementType;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.zag.validation.IdCardValidator;

/**
 * 身份证号码验证注解
 * 
 * @author stone 2017年5月11日
 */
@Target({FIELD,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=IdCardValidator.class)
public @interface IdCard {
    String message() default "身份证验证失败";  
    
    Class<?>[] groups() default {};  
     
    Class<? extends Payload>[] payload() default {}; 
    
	/**
	 * 当为false时,验证失败不会抛出异常
	 * 
	 * @return
	 */
	boolean throwable() default true;
}
