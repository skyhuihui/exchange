package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;
import com.zag.validation.constraints.IsNotNull;

public class NotNullValidator implements ConstraintValidator<IsNotNull, Object> {

	private String message;
//	private Exceptions type;
	private boolean throwable;
	@Override
	public void initialize(IsNotNull anno) {
		message=anno.message();
//		type = anno.throwing();
		throwable = anno.throwable();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value == null){
			if(throwable) {
				throw new BusinessException(Exceptions.Global.MISSING_REQUIRED_PARAMS,message);
			}
			return false;
		}
		return true;
	}

}
