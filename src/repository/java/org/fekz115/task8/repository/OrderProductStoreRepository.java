package org.fekz115.task8.repository;

import org.fekz115.task8.domain.OrderProductStore;

public interface OrderProductStoreRepository {

	OrderProductStore save(OrderProductStore orderProductStore);

	void delete(OrderProductStore orderProductStore);
}
