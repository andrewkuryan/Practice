package org.fekz115.task8.service.exception.office;

import org.fekz115.task8.service.exception.ServiceException;

public class OfficeWithTheSameAddressExistsException extends ServiceException {

    public OfficeWithTheSameAddressExistsException(String message) {
        super(message);
    }

    public OfficeWithTheSameAddressExistsException() {
        super("Office with the same address in this city already exists");
    }
}
