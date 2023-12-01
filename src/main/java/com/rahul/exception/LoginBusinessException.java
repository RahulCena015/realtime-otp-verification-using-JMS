package com.rahul.exception;

import java.time.LocalDateTime;

public class LoginBusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginBusinessException(String message,LocalDateTime time) {
		
		super(message);
		
	}

}
