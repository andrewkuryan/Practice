package org.fekz115.task8.service.exception.specification;

import org.fekz115.task8.service.exception.ServiceException;

public class SpecificationWithTheSameNameAlreadyExistsException extends ServiceException {

    public SpecificationWithTheSameNameAlreadyExistsException() {
        super("Specification with the same name already exists");
    }

    public SpecificationWithTheSameNameAlreadyExistsException(String message) {
        super(message);
    }

}
