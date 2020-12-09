package org.fekz115.task8.service;

import org.fekz115.task8.repository.CartRepository;
import org.fekz115.task8.repository.OrderProductStoreRepository;
import org.fekz115.task8.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringOrderService extends OrderService {
    public SpringOrderService(OrderRepository repository, CartRepository cartRepository, OrderProductStoreRepository productStoreRepository) {
        super(repository, cartRepository, productStoreRepository);
    }
}
