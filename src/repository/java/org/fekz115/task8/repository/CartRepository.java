package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Cart;

import java.util.Optional;

public interface CartRepository {

	Iterable<Cart> findByUserId(Integer id);

	Optional<Cart> findById(Integer id);

	Cart save(Cart cart);
}
