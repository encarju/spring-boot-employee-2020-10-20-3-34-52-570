package com.thoughtworks.springbootemployee.exception;

import static java.lang.String.format;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(int employeeId) {
        super(format("Employee with ID %d does not exist", employeeId));
    }
}
