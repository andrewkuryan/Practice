package org.fekz115.task8.repository;

import org.fekz115.task8.domain.CartProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartProductRepository extends
		CartProductRepository,
		CrudRepository<CartProduct, CartProduct.PrimaryKey> {

}
