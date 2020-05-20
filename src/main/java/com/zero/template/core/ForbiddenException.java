package com.zero.template.core;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AppException {
	
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String errorMessage) {
		super(errorMessage);
	}
	
	public String getErrorCode() {
		return "Unauthorized";
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.FORBIDDEN;
	}

}
