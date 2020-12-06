package org.fekz115.task8.repository;

import org.fekz115.task8.domain.DeliveryArea;

import java.util.Optional;

public interface DeliveryAreaRepository {

	Iterable<DeliveryArea> findAll();

	Optional<DeliveryArea> findById(Integer id);

	DeliveryArea save(DeliveryArea city);
}
