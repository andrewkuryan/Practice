package org.fekz115.task8.service;

import org.fekz115.task8.repository.ProductSpecificationsRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringProductSpecificationsService extends ProductSpecificationsService {
    public SpringProductSpecificationsService(ProductSpecificationsRepository repository) {
        super(repository);
    }
}
