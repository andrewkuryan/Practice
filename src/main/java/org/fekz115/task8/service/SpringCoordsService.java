package org.fekz115.task8.service;

import org.fekz115.task8.repository.CoordsRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringCoordsService extends CoordsService {

	public SpringCoordsService(CoordsRepository repository) {
		super(repository);
	}
}
