package org.fekz115.task8.service;

import org.fekz115.task8.domain.ProductSpecification;
import org.fekz115.task8.repository.ProductSpecificationsRepository;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Service;

public class ProductSpecificationsService {

    private final ProductSpecificationsRepository repository;

    public ProductSpecificationsService(ProductSpecificationsRepository repository) {
        this.repository = repository;
    }

    public void save(ProductSpecification productSpecification) throws ServiceException {
        repository.save(productSpecification);
    }

}
