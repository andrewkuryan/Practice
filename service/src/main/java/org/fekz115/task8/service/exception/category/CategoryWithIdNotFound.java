package org.fekz115.task8.service.exception.category;

import org.fekz115.task8.service.exception.ServiceException;

public class CategoryWithIdNotFound extends ServiceException {

    public CategoryWithIdNotFound(String message) {
        super(message);
    }

    public CategoryWithIdNotFound() {
        super("Category with this name not found");
    }
}
