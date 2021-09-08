package com.airbus.challenge.exception;

public class ProductServiceDBOperationException extends ProductServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3055499285806169579L;

	public ProductServiceDBOperationException() {
		
	}
	
	public ProductServiceDBOperationException(String error) {
		super(error);
	}
	
	public ProductServiceDBOperationException(Exception exception) {
		super(exception);
	}
	
	public ProductServiceDBOperationException(String error, Exception exception) {
		super(error, exception);
	}
}
