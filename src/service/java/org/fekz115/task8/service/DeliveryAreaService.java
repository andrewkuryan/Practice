package org.fekz115.task8.service;

import org.fekz115.task8.domain.CityDeliveryArea;
import org.fekz115.task8.domain.DeliveryArea;
import org.fekz115.task8.repository.CityDeliveryAreaRepository;
import org.fekz115.task8.repository.DeliveryAreaRepository;

import java.util.List;
import java.util.Optional;

public class DeliveryAreaService {

	protected final DeliveryAreaRepository repository;
	protected final CityDeliveryAreaRepository cityDeliveryAreaRepository;

	public DeliveryAreaService(
			DeliveryAreaRepository repository,
			CityDeliveryAreaRepository cityDeliveryAreaRepository
	) {
		this.repository = repository;
		this.cityDeliveryAreaRepository = cityDeliveryAreaRepository;
	}

	public Iterable<DeliveryArea> getDeliveryAreas() {
		return repository.findAll();
	}

	public Optional<DeliveryArea> getById(Integer id) {
		return id == null ? Optional.empty() : repository.findById(id);
	}

	public void save(
			DeliveryArea deliveryArea,
			List<CityDeliveryArea> newCities,
			List<Integer> oldCities
	) {
		var entity = repository.findById(deliveryArea.getId()).orElse(deliveryArea);
		entity.setName(deliveryArea.getName());
		entity.setColor(deliveryArea.getColor());
		entity.setStore(deliveryArea.getStore());
		entity.setEstimatedTime(deliveryArea.getEstimatedTime());

		repository.save(entity);

		var entityCities = entity.getCityDeliveryAreas();
		entityCities.forEach(value -> {
			if (!oldCities.contains(value.getCity().getId())) {
				cityDeliveryAreaRepository.delete(value);
			}
		});
		newCities.forEach(cityDeliveryAreaRepository::save);
	}
}
