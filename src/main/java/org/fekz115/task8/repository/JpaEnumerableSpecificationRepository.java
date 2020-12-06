package org.fekz115.task8.repository;

import org.fekz115.task8.domain.EnumerableSpecification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnumerableSpecificationRepository extends EnumerableSpecificationRepository, CrudRepository<EnumerableSpecification, Integer> {

}
