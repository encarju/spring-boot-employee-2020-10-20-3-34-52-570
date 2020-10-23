package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = NoEmployeeFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenericNotFoundException(NoEmployeeFoundException e) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND_ERROR", e.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setStatus((HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
