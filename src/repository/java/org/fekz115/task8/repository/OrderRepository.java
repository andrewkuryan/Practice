package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Order;

import java.util.Optional;

public interface OrderRepository {
    Iterable<Order> findAll();

    Order save(Order order);

    Optional<Order> findById(int id);
}
