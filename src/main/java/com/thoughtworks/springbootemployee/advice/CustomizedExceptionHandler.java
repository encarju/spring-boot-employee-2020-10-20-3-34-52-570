package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.exception.ErrorResponse;
import com.thoughtworks.springbootemployee.exception.InvalidCompanyAssociationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(value = {EmployeeNotFoundException.class, CompanyNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(Exception e) {
        return new ErrorResponse("NOT_FOUND_ERROR", e.getMessage(), NOT_FOUND.value());
    }

    @ExceptionHandler(value = InvalidCompanyAssociationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleInvalidAssociationException(Exception e) {
        return new ErrorResponse("ASSOCIATION_ERROR", e.getMessage(), BAD_REQUEST.value());
    }
}
