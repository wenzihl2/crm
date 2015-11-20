package com.wzbuaa.crm.exception;

@SuppressWarnings("serial")
public class UnLoginException extends RuntimeException {

	public UnLoginException(String e) {
		super(e);
	}

	public UnLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnLoginException(Throwable cause) {
		super(cause);
	}

}
