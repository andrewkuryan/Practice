package org.fekz115.task8.service.exception.userservice;

import org.fekz115.task8.service.exception.ServiceException;

public class UserWithTheSameEmailExistsException extends ServiceException {
    public UserWithTheSameEmailExistsException(String message) {
        super(message);
    }

    public UserWithTheSameEmailExistsException() {
        super("User with the same email exists");
    }
}
