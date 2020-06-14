package org.fekz115.task8.repository;

import org.fekz115.task8.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCityRepository extends CityRepository, CrudRepository<City, Integer> {}
