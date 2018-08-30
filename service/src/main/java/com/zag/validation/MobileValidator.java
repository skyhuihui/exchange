package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zag.validation.constraints.IsMobile;
import org.apache.commons.lang3.StringUtils;

import com.zag.core.exception.BusinessException;
import com.zag.exception.Exceptions;

/**
 * 手机号
 * 
 * @author stone 2017年5月17日
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String>{
	
	public static final MobileValidator INSTANCE = new MobileValidator();

	private static final String mobile_regexp = "^((14[0-9])|(13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
	private boolean required = true;
	private String message;
	private boolean throwable;
	@Override
	public void initialize(IsMobile anno) {
		message=anno.message();
//		type = anno.throwing();
		throwable = anno.throwable();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean pass = false;
		if(required){
			pass = StringUtils.isNotBlank(value) && value.matches(mobile_regexp);
		}else{
			pass = StringUtils.isBlank(value) || value.matches(mobile_regexp);
		}
		if(!pass && throwable){
			throw new BusinessException(Exceptions.User.MOBILE_ERROR,message);
		}
		return pass;
	}

}
