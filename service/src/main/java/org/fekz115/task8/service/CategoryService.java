package org.fekz115.task8.service;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.repository.CategoryRepository;
import org.fekz115.task8.service.exception.category.CategoryWithIdNotFound;
import org.fekz115.task8.service.exception.category.CategoryWithTheSameNameExistsException;

import java.util.Optional;

public class CategoryService {

    protected final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public void save(Category category) throws CategoryWithTheSameNameExistsException {
        if(repository.existsByNameAndIdNot(category.getName(), category.getId())) {
            throw new CategoryWithTheSameNameExistsException();
        } else {
            repository.save(category);
        }
    }

    public Iterable<Category> getCategories() {
        return repository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id) {
        return id == null ? Optional.empty() : repository.findById(id);
    }

    public void remove(Category category) throws CategoryWithIdNotFound {
        if(repository.existsById(category.getId())){
            repository.delete(category);
        } else throw new CategoryWithIdNotFound();
    }
}
