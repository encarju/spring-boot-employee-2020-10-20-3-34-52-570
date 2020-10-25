package com.thoughtworks.springbootemployee.exception;

import static java.lang.String.format;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(int id) {
        super(format("Employee with ID %d does not exist", id));
    }
}
