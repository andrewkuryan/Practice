package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSpecificationRepository extends SpecificationRepository, CrudRepository<Specification, Integer> {
}
