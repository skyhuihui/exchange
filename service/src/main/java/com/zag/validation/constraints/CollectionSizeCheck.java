package com.zag.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.zag.validation.CollectionSizeValidator;

/**
 * 集合范围检查
 * 
 * @author stone 2017年5月30日
 */
@Target({FIELD,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=CollectionSizeValidator.class)
public @interface CollectionSizeCheck {
	
    String message() default "";  
    
    int minSize() default 0;
    
    int maxSize() default Integer.MAX_VALUE;
    
    boolean throwable() default true;
	  
	Class<?>[] groups() default {};  
	   
	Class<? extends Payload>[] payload() default {}; 
}
