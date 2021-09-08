package com.airbus.challenge.response;

public class ApiResponse {

	private boolean success;
	private String message;
	private String field;
	
	public ApiResponse(boolean success, String message, String field) {
		this.success = success;
		this.message = message;
		this.field = field;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
