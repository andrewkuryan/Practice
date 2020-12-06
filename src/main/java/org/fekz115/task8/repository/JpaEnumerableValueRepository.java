package org.fekz115.task8.repository;

import org.fekz115.task8.domain.EnumerableValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnumerableValueRepository extends EnumerableValueRepository, CrudRepository<EnumerableValue, Integer> {

}
