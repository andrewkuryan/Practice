package org.fekz115.task8.service;

import org.fekz115.task8.repository.EnumerableSpecificationRepository;
import org.fekz115.task8.repository.EnumerableValueRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringEnumerableSpecificationService extends EnumerableSpecificationService {

	public SpringEnumerableSpecificationService(EnumerableSpecificationRepository repository, EnumerableValueRepository valueRepository) {
		super(repository, valueRepository);
	}
}
