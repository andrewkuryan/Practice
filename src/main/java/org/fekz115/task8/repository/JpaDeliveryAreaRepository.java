package org.fekz115.task8.repository;

import org.fekz115.task8.domain.DeliveryArea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDeliveryAreaRepository extends
		DeliveryAreaRepository,
		CrudRepository<DeliveryArea, Integer> {

}
