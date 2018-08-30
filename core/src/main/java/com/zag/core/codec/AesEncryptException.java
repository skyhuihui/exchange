package com.zag.core.codec;

/**
 * Created by stone on 11/16/15.
 */
public class AesEncryptException extends RuntimeException {

	private static final long serialVersionUID = -8831358639761706899L;

	public AesEncryptException() {
	}

	public AesEncryptException(String message) {
		super(message);
	}

	public AesEncryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public AesEncryptException(Throwable cause) {
		super(cause);
	}

	public AesEncryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
