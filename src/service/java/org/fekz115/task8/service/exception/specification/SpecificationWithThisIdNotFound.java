package org.fekz115.task8.service.exception.specification;

import org.fekz115.task8.service.exception.ServiceException;

public class SpecificationWithThisIdNotFound extends ServiceException {

    public SpecificationWithThisIdNotFound(String message) {
        super(message);
    }

    public SpecificationWithThisIdNotFound() {
        super("Specification with this id not found");
    }
}
