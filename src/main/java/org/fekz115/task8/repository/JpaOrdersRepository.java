package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrdersRepository extends OrderRepository, CrudRepository<Order, Integer> { }
