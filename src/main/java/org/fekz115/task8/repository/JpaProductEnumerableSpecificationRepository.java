package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductEnumerableSpecification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductEnumerableSpecificationRepository extends
		ProductEnumerableSpecificationsRepository,
		CrudRepository<ProductEnumerableSpecification, ProductEnumerableSpecification.PrimaryKey> {

}
