package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Specification;

import java.util.Optional;

public interface SpecificationRepository {

	boolean existsByNameAndIdNot(String name, Integer id);

	Specification save(Specification specification);

	Iterable<Specification> findAll();

	Optional<Specification> findById(Integer id);

	void delete(Specification specification);
}
