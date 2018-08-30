package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;
import com.zag.validation.constraints.StringLength;

public class StringLengthValidator implements ConstraintValidator<StringLength,String>{

	private int min;
	private int max;
	private boolean trim;
	private boolean notNull;
	private String message;
	
	@Override
	public void initialize(StringLength anno) {
		this.min = anno.min();
		this.max = anno.max();
		this.trim = anno.trim();
		this.notNull = anno.notNull();
		this.message = anno.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!notNull && value == null) return true;
		if(notNull && value == null) throw new BusinessException(Exceptions.Global.PARAMETER_ERROR,message);
		if(trim) value = value.trim();
		int len = value.length();
		if(len>=min && len <= max) return true;
		else throw new BusinessException(Exceptions.Global.PARAMETER_ERROR,message);
	}

}
