package com.zag.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.zag.core.asserts.BusinessAsserts;
import com.zag.exception.Exceptions;
import com.zag.validation.constraints.IdCard;

/**
 * 身份证号码验证器,验证规则请百度百科 "身份证号码"
 * 
 * @author stone 2017年5月11日
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {

	@Override
	public void initialize(IdCard constraintAnnotation) {

	}

	private static final int[] factors_17 = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private static final char[] mod_11 = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isBlank(value))		return true;	//空数据不验证
		if (value.trim().length() != 18)  	return false; 	//非空数据,先校验长度
		char[] chararray = value.trim().toCharArray();
		int len = chararray.length;
		char lastChar = chararray[len - 1];
		int sum = 0;
		for (int i = 0; i < len - 1; i++) {
			int digit = Integer.valueOf("" + chararray[i]);
			sum += digit * factors_17[i];
		}
		BusinessAsserts.isTrue(mod_11[sum % 11] == lastChar, Exceptions.Global.PARAMETER_ERROR,"身份证号码验证失败");
		return true;
	}
	public static void main(String[] args) {
		IdCardValidator v = new IdCardValidator();
		System.out.println(v.isValid("430421197710177894", null));
	}
}
