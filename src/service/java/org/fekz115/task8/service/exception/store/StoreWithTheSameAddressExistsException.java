package org.fekz115.task8.service.exception.store;

import org.fekz115.task8.service.exception.ServiceException;

public class StoreWithTheSameAddressExistsException extends ServiceException {

    public StoreWithTheSameAddressExistsException(String message) {
        super(message);
    }

    public StoreWithTheSameAddressExistsException() {
        super("Store with the same address in this city already exists");
    }
}
