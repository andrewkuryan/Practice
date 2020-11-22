package org.fekz115.task8.service;

import org.fekz115.task8.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringProductService extends ProductService {
    public SpringProductService(ProductRepository repository) {
        super(repository);
    }
}
