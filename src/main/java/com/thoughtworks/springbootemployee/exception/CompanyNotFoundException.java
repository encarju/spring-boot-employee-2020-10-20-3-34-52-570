package com.thoughtworks.springbootemployee.exception;

import static java.lang.String.format;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(int companyId) {
        super(format("Company with ID %d does not exist", companyId));
    }
}
