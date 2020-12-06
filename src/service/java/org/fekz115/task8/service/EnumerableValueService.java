package org.fekz115.task8.service;

import org.fekz115.task8.domain.EnumerableValue;
import org.fekz115.task8.repository.EnumerableValueRepository;

import java.util.Optional;

public class EnumerableValueService {

	protected final EnumerableValueRepository repository;

	public EnumerableValueService(EnumerableValueRepository repository) {
		this.repository = repository;
	}

	public Optional<EnumerableValue> findById(Integer id) {
		return id == null ? Optional.empty() : repository.findById(id);
	}

	public void save(EnumerableValue value) {
		repository.save(value);
	}

	public void remove(EnumerableValue value) {
		repository.delete(value);
	}
}
