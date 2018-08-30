package com.zag.core.exception;

/**
 * 所有需要捕获的异常的父类
 *
 * @author lei
 * @date 2017年10月31日
 * @reviewer
 * @see
 */
/**
 *
 * @author stone
 * @date 2017年7月28日
 * @reviewer
 */
public class RrbSystemException extends Exception{

    private static final long serialVersionUID = 6994564415657921473L;

    public RrbSystemException() {
        super();
    }

    public RrbSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RrbSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public RrbSystemException(String message) {
        super(message);
    }

    public RrbSystemException(Throwable cause) {
        super(cause);
    }
}
