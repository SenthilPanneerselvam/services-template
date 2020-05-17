package com.zero.template.core;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AppException {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String errorMessage) {
		super(errorMessage);
	}
	
	public HttpStatus getStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	public String getErrorCode() {
		return "BAD_REQUEST";
	}
	
}
