package org.fekz115.task8.service;

import org.fekz115.task8.domain.Cart;
import org.fekz115.task8.domain.CartProduct;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.repository.CartProductRepository;
import org.fekz115.task8.repository.CartRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class CartService {

	protected final CartRepository repository;
	private final CartProductRepository cartProductRepository;

	public CartService(CartRepository repository, CartProductRepository cartProductRepository) {
		this.repository = repository;
		this.cartProductRepository = cartProductRepository;
	}

	public Iterable<Cart> getByUserId(Integer id) {
		return id == null ? Collections.emptyList() : repository.findByUserId(id);
	}

	public Optional<Cart> getById(Integer id) {
		return repository.findById(id);
	}

	public Optional<Cart> getActiveCart(Optional<User> user) {
		return user.flatMap(value -> StreamSupport.stream(getByUserId(value.getId()).spliterator(), false)
				.filter(cart -> !cart.isOrdered())
				.findFirst());
	}

	public void save(List<CartProduct> newProducts, List<CartProduct> oldProducts) {
		oldProducts.forEach(cartProductRepository::delete);

		newProducts.forEach(cartProductRepository::save);
	}

	public Cart create(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setOrdered(false);
		cart.setTotalPrice(0.0);
		return repository.save(cart);
	}
}
