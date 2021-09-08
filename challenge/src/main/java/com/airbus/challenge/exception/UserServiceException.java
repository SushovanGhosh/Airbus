package com.airbus.challenge.exception;

public class UserServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8541946420957310857L;
	
	public UserServiceException() {
		
	}
	
	public UserServiceException(String error) {
		super(error);
	}
	
	public UserServiceException(Exception exception) {
		super(exception);
	}
	
	public UserServiceException(String error, Exception exception) {
		super(error, exception);
	}

}
