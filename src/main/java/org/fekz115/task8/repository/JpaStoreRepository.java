package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStoreRepository extends StoreRepository, CrudRepository<Store, Integer> {

}
