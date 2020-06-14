package org.fekz115.task8.service;

import org.fekz115.task8.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringCityService extends CityService {
    public SpringCityService(CityRepository repository) {
        super(repository);
    }
}
