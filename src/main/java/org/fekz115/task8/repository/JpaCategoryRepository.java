package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends CategoryRepository, CrudRepository<Category, Integer> {
}
