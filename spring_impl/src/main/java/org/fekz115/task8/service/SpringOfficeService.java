package org.fekz115.task8.service;

import org.fekz115.task8.repository.OfficeRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringOfficeService extends OfficeService {
    public SpringOfficeService(OfficeRepository repository) {
        super(repository);
    }
}
