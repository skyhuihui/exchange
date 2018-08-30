package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.validation.constraints.IsNotBlank;
import org.apache.commons.lang3.StringUtils;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;

public class NotBlankValidator implements ConstraintValidator<IsNotBlank, String> {

	private String message;
//	private Exceptions type;
	private boolean throwable;
	@Override
	public void initialize(IsNotBlank anno) {
		message=anno.message();
//		type = anno.throwing();
		throwable = anno.throwable();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value)){
			if(throwable) {
				throw new BusinessException(Exceptions.Global.MISSING_REQUIRED_PARAMS,message);
			}
			return false;
		}
		return true;
	}

}
