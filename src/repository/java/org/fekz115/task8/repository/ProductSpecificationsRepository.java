package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductSpecification;

public interface ProductSpecificationsRepository {

	ProductSpecification save(ProductSpecification productSpecification);

	void delete(ProductSpecification productSpecification);
}
