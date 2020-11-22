package org.fekz115.task8.service.exception.userservice;

import org.fekz115.task8.service.exception.ServiceException;

public class UserWithTheSameLoginExistsException extends ServiceException {
    public UserWithTheSameLoginExistsException(String message) {
        super(message);
    }

    public UserWithTheSameLoginExistsException() {
        super("User with the same login exists");
    }
}
