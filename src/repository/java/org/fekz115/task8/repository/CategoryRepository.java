package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Category;

import java.util.Optional;

public interface CategoryRepository {

    boolean existsByNameAndIdNot(String name, Integer id);

    Category save(Category category);

    Iterable<Category> findAll();

    Optional<Category> findById(Integer id);

    boolean existsById(int id);

    void delete(Category category);
}
