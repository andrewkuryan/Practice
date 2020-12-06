package org.fekz115.task8.repository;

import org.fekz115.task8.domain.EnumerableSpecification;

import java.util.Optional;

public interface EnumerableSpecificationRepository {

	boolean existsByNameAndIdNot(String name, Integer id);

	EnumerableSpecification save(EnumerableSpecification specification);

	Iterable<EnumerableSpecification> findAll();

	Optional<EnumerableSpecification> findById(Integer id);

	void delete(EnumerableSpecification specification);
}
