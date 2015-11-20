package com.wzbuaa.crm.exception;

/**
 * @author zhenglong 2008-5-8
 */
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException{
	
	public ApplicationException(String e) {
		super(e);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}
}
