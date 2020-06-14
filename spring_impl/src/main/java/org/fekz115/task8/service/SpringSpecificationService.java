package org.fekz115.task8.service;

import org.fekz115.task8.repository.SpecificationRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringSpecificationService extends SpecificationService {
    public SpringSpecificationService(SpecificationRepository repository) {
        super(repository);
    }
}
