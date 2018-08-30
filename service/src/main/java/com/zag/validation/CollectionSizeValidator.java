package com.zag.validation;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;
import com.zag.validation.constraints.CollectionSizeCheck;

/**
 * 集合元素数量检查
 * 
 * @author stone 2017年5月30日
 */
public class CollectionSizeValidator implements ConstraintValidator<CollectionSizeCheck,Collection<?>>{

	private int min;
	private int max;
	private boolean throwable;
	private String message;
	
	@Override
	public void initialize(CollectionSizeCheck anno) {
		this.max = anno.maxSize();
		this.min = anno.minSize();
		this.throwable = anno.throwable();
		this.message = anno.message();
	}

	@Override
	public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
		int size = value.size();
		if(size >= min && size <= max){
			return true;
		}
		
		
		if(throwable){
			throw new BusinessException(Exceptions.Global.PARAMETER_ERROR,message);
		}else{
			return false;
		}
			
	}

}
