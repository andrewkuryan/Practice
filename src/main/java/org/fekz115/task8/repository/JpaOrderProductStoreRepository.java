package org.fekz115.task8.repository;

import org.fekz115.task8.domain.OrderProductStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderProductStoreRepository extends
		OrderProductStoreRepository,
		CrudRepository<OrderProductStore, OrderProductStore.PrimaryKey> {

}
