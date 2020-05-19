package com.zero.template.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<GenericResponse> handleError (Exception ex, WebRequest request) {
    	ex.printStackTrace();
    	GenericResponse response = new GenericResponse();
        response.addError("Server Error", "Something terrible happened, Please contact system administrator");
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(value = { AppException.class })
    protected ResponseEntity<GenericResponse> handleConflict(AppException ex, WebRequest request) {
        GenericResponse response = new GenericResponse();
        response.addError(ex.getErrorCode(), ex.getErrorMessage());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }
}
