package org.fekz115.task8.repository;

import org.fekz115.task8.domain.City;

import java.util.Optional;

public interface CityRepository {

    boolean existsByNameAndIdNot(String name, Integer id);

    City save(City city);

    Iterable<City> findAll();

    boolean existsById(int id);

    void delete(City city);

    Optional<City> findById(Integer id);
}
