package com.thoughtworks.springbootemployee.exception;

import static java.lang.String.format;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException(int id) {
        super(format("Employee with ID %d does not exist", id));
    }
}
