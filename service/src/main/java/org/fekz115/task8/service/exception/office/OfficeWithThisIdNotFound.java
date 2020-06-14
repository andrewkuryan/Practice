package org.fekz115.task8.service.exception.office;

import org.fekz115.task8.service.exception.ServiceException;

public class OfficeWithThisIdNotFound extends ServiceException {

    public OfficeWithThisIdNotFound(String message) {
        super(message);
    }

    public OfficeWithThisIdNotFound() {
        super("Office with this id not found");
    }
}
