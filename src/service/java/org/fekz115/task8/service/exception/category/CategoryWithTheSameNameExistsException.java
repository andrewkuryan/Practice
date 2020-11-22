package org.fekz115.task8.service.exception.category;

import org.fekz115.task8.service.exception.ServiceException;

public class CategoryWithTheSameNameExistsException extends ServiceException {

    public CategoryWithTheSameNameExistsException(String message) {
        super(message);
    }

    public CategoryWithTheSameNameExistsException() {
        super("Category with the same name already exists");
    }
}
