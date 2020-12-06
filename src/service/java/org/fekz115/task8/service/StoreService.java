package org.fekz115.task8.service;

import org.fekz115.task8.domain.Coords;
import org.fekz115.task8.domain.ProductStore;
import org.fekz115.task8.domain.Store;
import org.fekz115.task8.repository.CoordsRepository;
import org.fekz115.task8.repository.ProductStoreRepository;
import org.fekz115.task8.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

public class StoreService {

	protected final StoreRepository storeRepository;
	protected final CoordsRepository coordsRepository;
	private final ProductStoreRepository productStoreRepository;

	public StoreService(
			StoreRepository storeRepository,
			CoordsRepository coordsRepository,
			ProductStoreRepository productStoreRepository
	) {
		this.storeRepository = storeRepository;
		this.coordsRepository = coordsRepository;
		this.productStoreRepository = productStoreRepository;
	}

	public Optional<Store> getById(Integer id) {
		return storeRepository.findById(id);
	}

	public void save(
			Store store, Coords coords,
			List<ProductStore> newProducts,
			List<Integer> oldProducts
	) {
		var entity = storeRepository.findById(store.getId()).orElse(store);
		entity.setAddress(store.getAddress());
		entity.setPhone(store.getPhone());
		entity.setCity(store.getCity());

		var storeCoords = entity.getCoords();
		if (storeCoords == null) {
			entity.setCoords(coords);
		} else {
			storeCoords.setLatitude(coords.getLatitude());
			storeCoords.setLongitude(coords.getLongitude());
		}
		coordsRepository.save(entity.getCoords());
		storeRepository.save(entity);

		var entityProducts = entity.getProductStores();
		entityProducts.forEach(value -> {
			if (!oldProducts.contains(value.getProduct().getId())) {
				productStoreRepository.delete(value);
			}
		});

		newProducts.forEach(productStoreRepository::save);
	}
}
