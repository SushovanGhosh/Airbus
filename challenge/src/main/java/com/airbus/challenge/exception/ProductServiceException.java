package com.airbus.challenge.exception;

public class ProductServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 332628141259131596L;

	public ProductServiceException() {
		
	}
	
	public ProductServiceException(String error) {
		super(error);
	}
	
	public ProductServiceException(Exception exception) {
		super(exception);
	}
	
	public ProductServiceException(String error, Exception exception) {
		super(error, exception);
	}
}
