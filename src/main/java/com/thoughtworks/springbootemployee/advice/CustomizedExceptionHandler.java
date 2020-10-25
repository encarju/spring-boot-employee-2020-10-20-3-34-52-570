package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    private final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";

    @ExceptionHandler(value = NoEmployeeFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleGenericNotFoundException(NoEmployeeFoundException e) {
        return new ErrorResponse(NOT_FOUND_ERROR,
                e.getMessage(),
                NOT_FOUND.value(),
                now());
    }
}
