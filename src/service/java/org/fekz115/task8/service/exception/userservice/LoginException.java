package org.fekz115.task8.service.exception.userservice;

import org.fekz115.task8.service.exception.ServiceException;

public class LoginException extends ServiceException {

    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        super("Invalid login or password, try again");
    }

}
