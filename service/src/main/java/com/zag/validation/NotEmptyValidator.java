package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;
import com.zag.validation.constraints.IsNotEmpty;

public class NotEmptyValidator implements ConstraintValidator<IsNotEmpty, Iterable<?>> {

	private String message;
//	private Exceptions type;
	private boolean throwable;
	@Override
	public void initialize(IsNotEmpty anno) {
		message=anno.message();
//		type = anno.throwing();
		throwable = anno.throwable();
	}

	@Override
	public boolean isValid(Iterable<?> value, ConstraintValidatorContext context) {
		if(value == null || !value.iterator().hasNext()){
			if(throwable) {
				throw new BusinessException(Exceptions.Global.MISSING_REQUIRED_PARAMS,message);
			}
			return false;
		}
		return true;
	}

}
