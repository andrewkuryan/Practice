package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductStore;

public interface ProductStoreRepository {

	ProductStore save(ProductStore productStore);

	void delete(ProductStore productStore);
}
