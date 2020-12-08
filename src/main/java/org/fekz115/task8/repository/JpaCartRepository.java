package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository extends CartRepository, CrudRepository<Cart, Integer> {

}
