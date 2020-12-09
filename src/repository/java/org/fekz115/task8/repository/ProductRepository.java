package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Product;

import java.util.Optional;

public interface ProductRepository {

	Product save(Product product);

	Optional<Product> findById(int id);

	void delete(Product product);

	Optional<Product> findById(Integer id);
}
