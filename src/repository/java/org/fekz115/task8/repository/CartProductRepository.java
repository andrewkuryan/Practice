package org.fekz115.task8.repository;

import org.fekz115.task8.domain.CartProduct;

public interface CartProductRepository {

	CartProduct save(CartProduct cartProduct);

	void delete(CartProduct cartProduct);
}
