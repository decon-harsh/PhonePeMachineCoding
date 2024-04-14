package com.example.machineCoding.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse  = ErrorResponse.builder(ex,ex.getHttpStatus(),ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse  = ErrorResponse.builder(ex,ex.getHttpStatus(),ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponse  = ErrorResponse.builder(ex,ex.getHttpStatus(),ex.getMessage()).build();
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}