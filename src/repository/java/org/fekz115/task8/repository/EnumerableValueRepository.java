package org.fekz115.task8.repository;

import org.fekz115.task8.domain.EnumerableValue;

import java.util.Optional;

public interface EnumerableValueRepository {

	Optional<EnumerableValue> findById(int id);

	EnumerableValue save(EnumerableValue value);

	void delete(EnumerableValue value);
}
