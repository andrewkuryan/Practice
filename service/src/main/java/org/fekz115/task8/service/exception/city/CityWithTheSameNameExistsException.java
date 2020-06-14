package org.fekz115.task8.service.exception.city;

import org.fekz115.task8.service.exception.ServiceException;

public class CityWithTheSameNameExistsException extends ServiceException {

    public CityWithTheSameNameExistsException(String message) {
        super(message);
    }

    public CityWithTheSameNameExistsException() {
        super("City with the same name already exists");
    }
}
