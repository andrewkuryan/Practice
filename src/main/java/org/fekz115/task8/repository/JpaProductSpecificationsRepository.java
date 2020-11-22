package org.fekz115.task8.repository;

import org.fekz115.task8.domain.ProductSpecification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductSpecificationsRepository extends ProductSpecificationsRepository, CrudRepository<ProductSpecification, ProductSpecification.PrimaryKey> {}
