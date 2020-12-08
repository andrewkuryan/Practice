package org.fekz115.task8.service;

import org.fekz115.task8.repository.CartProductRepository;
import org.fekz115.task8.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class SpringCartService extends CartService {

	public SpringCartService(CartRepository cartRepository, CartProductRepository cartProductRepository) {
		super(cartRepository, cartProductRepository);
	}
}
