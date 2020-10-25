package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    private static final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEmployeeNotFoundException(Exception e) {
        return new ErrorResponse(NOT_FOUND_ERROR,
                e.getMessage(),
                NOT_FOUND.value(),
                now());
    }

    @ExceptionHandler(value = CompanyNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleCompanyNotFoundException(Exception e) {
        return new ErrorResponse(NOT_FOUND_ERROR,
                e.getMessage(),
                NOT_FOUND.value(),
                now());
    }
}
