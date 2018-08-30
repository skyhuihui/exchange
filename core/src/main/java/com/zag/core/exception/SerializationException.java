package com.zag.core.exception;

public class SerializationException extends SystemException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6285871793188767403L;

	public SerializationException(String s) {
        super(s);
    }

    public SerializationException(String s, Throwable e) {
        super(s, e);
    }

    public SerializationException(Throwable e) {
        super(e);
    }
}

