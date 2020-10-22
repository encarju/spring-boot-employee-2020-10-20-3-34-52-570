package com.thoughtworks.springbootemployee.exception;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException() {
        super("No Employee Found in the List");
    }
}
