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
        GenericResponse response = new GenericResponse();
        response.addError("Server Error", "Something terrible happened, Please contact system administrator");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<GenericResponse> handleConflict(BadRequestException ex, WebRequest request) {
        GenericResponse response = new GenericResponse();
        response.addError("BAD_REQUEST", ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
