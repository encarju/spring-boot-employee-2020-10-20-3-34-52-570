package com.thoughtworks.springbootemployee.exception;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException(int id) {
        super(String.format("Employee with ID %d does not exist", id));
    }
}
