package org.fekz115.task8.repository;

import org.fekz115.task8.domain.CityDeliveryArea;

public interface CityDeliveryAreaRepository {

	CityDeliveryArea save(CityDeliveryArea cityDeliveryArea);

	void delete(CityDeliveryArea cityDeliveryArea);
}
