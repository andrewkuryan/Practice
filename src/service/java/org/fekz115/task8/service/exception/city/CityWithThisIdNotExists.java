package org.fekz115.task8.service.exception.city;

import org.fekz115.task8.service.exception.ServiceException;

public class CityWithThisIdNotExists extends ServiceException {

    public CityWithThisIdNotExists(String message) {
        super(message);
    }

    public CityWithThisIdNotExists() {
        super("City with this id not exists");
    }
}
