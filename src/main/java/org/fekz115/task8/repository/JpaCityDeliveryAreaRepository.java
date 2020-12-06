package org.fekz115.task8.repository;

import org.fekz115.task8.domain.CityDeliveryArea;
import org.springframework.data.repository.CrudRepository;

public interface JpaCityDeliveryAreaRepository extends
		CityDeliveryAreaRepository,
		CrudRepository<CityDeliveryArea, Integer> {

}
