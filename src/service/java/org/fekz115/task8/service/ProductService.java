package org.fekz115.task8.service;

import org.fekz115.task8.domain.Product;
import org.fekz115.task8.domain.ProductEnumerableSpecification;
import org.fekz115.task8.domain.ProductSpecification;
import org.fekz115.task8.repository.ProductEnumerableSpecificationsRepository;
import org.fekz115.task8.repository.ProductRepository;
import org.fekz115.task8.repository.ProductSpecificationsRepository;
import org.fekz115.task8.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class ProductService {

	protected final ProductRepository repository;
	protected final ProductSpecificationsRepository specificationsRepository;
	protected final ProductEnumerableSpecificationsRepository enumerableSpecificationsRepository;

	public ProductService(
			ProductRepository repository,
			ProductSpecificationsRepository specificationsRepository,
			ProductEnumerableSpecificationsRepository enumerableSpecificationsRepository
	) {
		this.repository = repository;
		this.specificationsRepository = specificationsRepository;
		this.enumerableSpecificationsRepository = enumerableSpecificationsRepository;
	}

	public void save(
			Product product,
			List<ProductSpecification> newSpecifications,
			List<Integer> oldSpecificationIds,
			List<ProductEnumerableSpecification> newEnumerables,
			List<ProductEnumerableSpecification.PrimaryKey> oldEnumerableIds
	) {
		var entity = repository.findById(product.getId()).orElse(product);
		entity.setName(product.getName());
		entity.setDescription(product.getDescription());
		entity.setPrice(product.getPrice());
		repository.save(entity);

		var productSpecifications = entity.getSpecifications();
		productSpecifications.forEach(value -> {
			if (!oldSpecificationIds.contains(value.getSpecification().getId())) {
				specificationsRepository.delete(value);
			}
		});

		newSpecifications.forEach(specificationsRepository::save);

		var productEnumerables = entity.getEnumerableSpecifications();
		productEnumerables.forEach(value -> {
			if (!oldEnumerableIds.contains(value.getPrimaryKey())) {
				enumerableSpecificationsRepository.delete(value);
			}
		});

		newEnumerables.forEach(enumerableSpecificationsRepository::save);
	}

	public Optional<Product> getProduct(Product product) {
		return repository.findById(product.getId());
	}

	public void remove(Product product) throws ServiceException {
		repository.delete(product);
	}
}
