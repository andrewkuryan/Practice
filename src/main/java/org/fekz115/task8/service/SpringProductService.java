package org.fekz115.task8.service;

import org.fekz115.task8.repository.ProductEnumerableSpecificationsRepository;
import org.fekz115.task8.repository.ProductRepository;
import org.fekz115.task8.repository.ProductSpecificationsRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringProductService extends ProductService {
    public SpringProductService(
            ProductRepository repository,
            ProductSpecificationsRepository specificationsRepository,
            ProductEnumerableSpecificationsRepository enumerableSpecificationsRepository
    ) {
        super(repository, specificationsRepository, enumerableSpecificationsRepository);
    }
}
