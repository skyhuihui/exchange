package com.zag.exception;

import com.zag.core.exception.BusinessException;
import com.zag.core.exception.ExceptionType;

/**
 * 参数不合法
 *
 * @author lei
 * @date 2017年05月02日
 * @reviewer
 * @see
 */
public class IllegalParameterException extends BusinessException {
    private static final long serialVersionUID = 6191216245233367781L;

    private static final ExceptionType exceptionType = Exceptions.Global.ILLEGAL_PARAMETER_ERROR;

    public IllegalParameterException(String message) {
        super(exceptionType, message);
    }
}
