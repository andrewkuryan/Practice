package org.fekz115.task8.service;

import org.fekz115.task8.repository.EnumerableValueRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringEnumerableValueService extends EnumerableValueService {

	public SpringEnumerableValueService(EnumerableValueRepository repository) {
		super(repository);
	}
}
