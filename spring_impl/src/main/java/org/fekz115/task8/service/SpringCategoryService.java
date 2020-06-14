package org.fekz115.task8.service;

import org.fekz115.task8.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringCategoryService extends CategoryService {
    public SpringCategoryService(CategoryRepository repository) {
        super(repository);
    }
}
