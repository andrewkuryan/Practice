package org.fekz115.task8.service.tests.mocks;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MockCategoryRepository implements CategoryRepository {

    List<Category> categoryList = new ArrayList<>();

    @Override
    public boolean existsByNameAndIdNot(String name, Integer id) {
        return categoryList.stream()
                .anyMatch(x -> x.getId() != id && x.getName().equals(name));
    }

    @Override
    public Category save(Category category) {
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());
        if(category.getId() < categoryList.size() && category.getId() == 0) {
            category.setId(categoryList.size());
            categoryToSave.setId(category.getId());
            categoryList.add(categoryToSave);
        } else {
            categoryToSave.setId(category.getId());
            categoryList.add(category.getId(), categoryToSave);
        }
        return category;
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryList.stream()
                .map(x -> {
                    Category category = new Category();
                    category.setId(x.getId());
                    category.setName(x.getName());
                    return category;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(Integer id) {
        Optional<Category> category = categoryList.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
        if(category.isPresent()) {
            Category ans = new Category();
            ans.setId(category.get().getId());
            ans.setName(category.get().getName());
            return Optional.of(ans);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(int id) {
        return categoryList.stream()
                .anyMatch(x -> x.getId() == id);
    }

    @Override
    public void delete(Category category) {
        categoryList.remove(category.getId());
    }

}
