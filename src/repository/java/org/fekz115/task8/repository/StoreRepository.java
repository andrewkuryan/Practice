package org.fekz115.task8.repository;

import org.fekz115.task8.domain.City;
import org.fekz115.task8.domain.Store;

import java.util.Optional;

public interface StoreRepository {

	Iterable<Store> findAll();

	Optional<Store> findById(Integer id);

	Store save(Store store);

	boolean existsByAddressAndCityAndIdNot(String address, City city, Integer id);
}
