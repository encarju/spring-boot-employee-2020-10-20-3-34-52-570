package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    private final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";

    @ExceptionHandler(value = NoEmployeeFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenericNotFoundException(NoEmployeeFoundException e) {
        return new ErrorResponse(NOT_FOUND_ERROR,
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
    }
}
