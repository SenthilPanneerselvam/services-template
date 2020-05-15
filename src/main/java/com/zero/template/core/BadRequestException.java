package com.zero.template.core;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public BadRequestException(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
