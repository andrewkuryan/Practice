package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Cart;

public interface CartRepository {

	Iterable<Cart> findByUserId(Integer id);

	Cart save(Cart cart);
}
