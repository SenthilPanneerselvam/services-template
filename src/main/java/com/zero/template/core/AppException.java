package com.zero.template.core;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppException() {}
	
	public AppException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private String errorMessage;
	
	private String errorCode;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public abstract HttpStatus getStatus();

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
