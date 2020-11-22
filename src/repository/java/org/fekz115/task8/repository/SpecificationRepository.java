package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.domain.Specification;

import java.util.Optional;

public interface SpecificationRepository {

    boolean existsByNameAndCategoryAndIdNot(String name, Category category, Integer id);

    Specification save(Specification specification);

    Optional<Specification> findById(Integer id);

    void delete(Specification specification);
}
