package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Coords;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCoordsRepository extends CoordsRepository, CrudRepository<Coords, Integer> {

}
