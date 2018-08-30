package com.zag.core.codec;

/**
 * Created by stone on 11/16/15.
 */
public class AesDecryptException extends RuntimeException {

	private static final long serialVersionUID = -5917283691756557660L;

	public AesDecryptException() {
	}

	public AesDecryptException(String message) {
		super(message);
	}

	public AesDecryptException(String message, Throwable cause) {
		super(message, cause);
	}

	public AesDecryptException(Throwable cause) {
		super(cause);
	}

	public AesDecryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
