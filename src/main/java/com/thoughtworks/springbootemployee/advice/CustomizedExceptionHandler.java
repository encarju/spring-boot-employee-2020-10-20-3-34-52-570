package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleEmployeeNotFoundException(Exception e) {
        return new ErrorResponse(e.getMessage(), NOT_FOUND.value());
    }

    @ExceptionHandler(value = CompanyNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleCompanyNotFoundException(Exception e) {
        return new ErrorResponse(e.getMessage(), NOT_FOUND.value());
    }
}
