package com.zero.template.core;

import org.springframework.http.HttpStatus;

public class UnAuthenticatedException extends AppException {

	private static final long serialVersionUID = 1L;
	
	public UnAuthenticatedException(String errorMessage) {
		super(errorMessage);
	}

	public String getErrorCode() {
		return "UN_AUTHENTICATED";
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

}
