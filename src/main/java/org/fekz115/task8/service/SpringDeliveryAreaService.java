package org.fekz115.task8.service;

import org.fekz115.task8.repository.CityDeliveryAreaRepository;
import org.fekz115.task8.repository.DeliveryAreaRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringDeliveryAreaService extends DeliveryAreaService {

	public SpringDeliveryAreaService(DeliveryAreaRepository repository, CityDeliveryAreaRepository cityDeliveryAreaRepository) {
		super(repository, cityDeliveryAreaRepository);
	}
}
