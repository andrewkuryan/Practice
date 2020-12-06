package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductEnumerableSpecification;

public interface ProductEnumerableSpecificationsRepository {

	ProductEnumerableSpecification save(ProductEnumerableSpecification productSpecification);

	void delete(ProductEnumerableSpecification productSpecification);
}
