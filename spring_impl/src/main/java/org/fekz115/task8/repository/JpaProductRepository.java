package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends ProductRepository, CrudRepository<Product, Integer> {}
