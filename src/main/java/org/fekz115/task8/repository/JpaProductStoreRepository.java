package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductStoreRepository extends
		ProductStoreRepository,
		CrudRepository<ProductStore, ProductStore.PrimaryKey> {

}
