package org.fekz115.task8.service;

import org.fekz115.task8.domain.Cart;
import org.fekz115.task8.domain.Order;
import org.fekz115.task8.domain.OrderProductStore;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.repository.CartRepository;
import org.fekz115.task8.repository.OrderProductStoreRepository;
import org.fekz115.task8.repository.OrderRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderService {

	protected final OrderRepository repository;
	protected final CartRepository cartRepository;
	protected final OrderProductStoreRepository productStoreRepository;

	public OrderService(OrderRepository repository, CartRepository cartRepository, OrderProductStoreRepository productStoreRepository) {
		this.repository = repository;
		this.productStoreRepository = productStoreRepository;
		this.cartRepository = cartRepository;
	}

	public Iterable<Order> getAll() {
		return repository.findAll();
	}

	public Iterable<Order> getUsers(User user) {
		return StreamSupport.stream(cartRepository.findByUserId(user.getId()).spliterator(), false)
				.map(Cart::getOrder)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public void save(Order order, List<OrderProductStore> productStores) {
		repository.save(order);

		productStores.forEach(productStoreRepository::save);
	}

	public void setStatus(int id, String status) {
		repository.findById(id).ifPresent(order -> {
			if (!order.getStatus().equals(status)) {
				order.setStatus(status);
				repository.save(order);

				if (status.equals("Canceled") || status.equals("Declined")) {
					order.getOrderProductStores().forEach(productStoreRepository::delete);
				}
			}
		});
	}
}
