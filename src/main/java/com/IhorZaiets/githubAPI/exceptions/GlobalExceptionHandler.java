package com.IhorZaiets.githubAPI.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBodyExceptionDetails> handleNotFoundException(ResourceNotFoundException exception) {
        return makeResponse(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseBodyExceptionDetails> handleRuntimeException(RuntimeException exception) {
        return makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, ExceptionMessage.CONTACT_ADMINISTRATOR);
    }

    private ResponseEntity<ResponseBodyExceptionDetails> makeResponse(HttpStatus status, Exception exception) {
        return makeResponse(status, exception, exception.getMessage());
    }

    private ResponseEntity<ResponseBodyExceptionDetails> makeResponse(HttpStatus status, Exception exception, String exceptionMessage) {
        exception.printStackTrace();
        return new ResponseEntity<>(new ResponseBodyExceptionDetails(status, exceptionMessage), status);
    }
}
