package com.zag.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.zag.validation.StringLengthValidator;

@Target({ FIELD, METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringLengthValidator.class)
public @interface StringLength {

	int min() default 0;

	int max() default Integer.MAX_VALUE;
	
	boolean trim() default true;
	
	/**
	 * 如果字符串为null,切notNull为fasle,则不进行接下来的检查
	 * @author stone
	 * @date 2017年8月6日
	 * @return
	 */
	boolean notNull() default false;
	
	String message() default "长度不符合";  
    
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
