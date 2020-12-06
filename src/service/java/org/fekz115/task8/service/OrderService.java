package org.fekz115.task8.service;

import org.fekz115.task8.domain.Order;
import org.fekz115.task8.repository.OrderRepository;

public class OrderService {

	protected final OrderRepository repository;

	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}

	public Iterable<Order> getAll() {
		return repository.findAll();
	}

	public void save(Order order) {
		repository.save(order);
	}

	public void setStatus(int id, String status) {
		repository.findById(id).ifPresent(order -> {
			order.setStatus(status);
			repository.save(order);
		});
	}

	public void remove(int id) {
		repository.deleteById(id);
	}
}
