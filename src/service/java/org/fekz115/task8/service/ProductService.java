package org.fekz115.task8.service;

import org.fekz115.task8.domain.Product;
import org.fekz115.task8.repository.ProductRepository;
import org.fekz115.task8.service.exception.ServiceException;

import java.util.Optional;

public class ProductService {

	protected final ProductRepository repository;

	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	public void save(Product product) throws ServiceException {
		var entity = repository.findById(product.getId()).orElse(product);
		entity.setName(product.getName());
		entity.setDescription(product.getDescription());
		repository.save(entity);
	}

	public Optional<Product> getProduct(Product product) {
		return repository.findById(product.getId());
	}

	public void remove(Product product) throws ServiceException {
		repository.delete(product);
	}
}
