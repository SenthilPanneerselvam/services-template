package com.zero.template.configs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.zero.template.dtos.GenericResponse;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
 
    @org.springframework.web.bind.annotation.ExceptionHandler(value 
      = { Exception.class })
    protected ResponseEntity<GenericResponse> handleConflict(
      RuntimeException ex, WebRequest request) {
        GenericResponse response = new GenericResponse();
        response.addError("Server Error", "Something terrible happened, Please contact system administrator");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
