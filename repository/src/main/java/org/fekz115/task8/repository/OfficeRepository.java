package org.fekz115.task8.repository;

import org.fekz115.task8.domain.City;
import org.fekz115.task8.domain.Office;

import java.util.Optional;

public interface OfficeRepository{

    boolean existsByAddressAndCityAndIdNot(String address, City city, Integer id);

    Office save(Office office);

    Optional<Office> findById(Integer id);

    boolean existsById(int id);

    void delete(Office office);

    Iterable<Office> findAll();

}
