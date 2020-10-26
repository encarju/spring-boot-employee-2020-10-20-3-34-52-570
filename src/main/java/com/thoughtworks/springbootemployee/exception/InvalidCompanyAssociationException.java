package com.thoughtworks.springbootemployee.exception;

import static java.lang.String.format;

public class InvalidCompanyAssociationException extends RuntimeException {
    public InvalidCompanyAssociationException(int companyId) {
        super(format("Unable to associate with non-existing company of ID %d", companyId));
    }
}
