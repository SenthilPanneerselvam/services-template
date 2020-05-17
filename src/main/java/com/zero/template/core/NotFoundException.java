package com.zero.template.core;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {
	
	private static final long serialVersionUID = 1L;

	public NotFoundException(String errorMessage) {
		super(errorMessage);
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.NOT_FOUND;
	}
	
	@Override
	public String getErrorCode() {
		return "NOT_FOUND";
	}

}
