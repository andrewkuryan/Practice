package org.fekz115.task8.service;

import org.fekz115.task8.repository.CoordsRepository;
import org.fekz115.task8.repository.ProductStoreRepository;
import org.fekz115.task8.repository.StoreRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringStoreService extends StoreService {

	public SpringStoreService(
			StoreRepository repository,
			CoordsRepository coordsRepository,
			ProductStoreRepository productStoreRepository
	) {
		super(repository, coordsRepository, productStoreRepository);
	}
}
