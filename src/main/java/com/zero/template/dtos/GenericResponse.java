package com.zero.template.dtos;

import java.util.ArrayList;
import java.util.List;

public class GenericResponse {
	
	public GenericResponse() {
		
	}
	
	public GenericResponse(Object data) {
		this.data = data;
	}
	
	private List<Error> errors;
	
	private Object data;

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	public void addError(Error error) {
		if(errors == null)
			errors = new ArrayList<Error>();
		errors.add(error);
	}
	
	public void addError(String errorCode, String errorMessage) {
		if(errors == null)
			errors = new ArrayList<Error>();
		errors.add(new Error());
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
