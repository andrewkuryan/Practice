package org.fekz115.task8.service.tests;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.repository.CategoryRepository;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.exception.category.CategoryWithIdNotFound;
import org.fekz115.task8.service.exception.category.CategoryWithTheSameNameExistsException;
import org.fekz115.task8.service.tests.mocks.MockCategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// This types of tests common for services of all next classes: Category, City, Office, Order, Product, User, Specification, ProductSpecification
public class CategoryServiceTests {

    CategoryRepository repository;
    CategoryService service;

    @BeforeEach
    void init () {

        repository = new MockCategoryRepository();
        service = new CategoryService(repository);

        Category category = new Category();
        category.setName("Category1");
        repository.save(category);
        category = new Category();
        category.setName("Category2");
        repository.save(category);

    }

    @Test // if we try to save new category with the same name it must throw exception
    void saveWithTheSameNameTest() {
        Assertions.assertThrows(CategoryWithTheSameNameExistsException.class, () -> {
            Category category = new Category();
            category.setName("Category2");
            service.save(category);
        });
    }

    @Test // if we edit category it must work without exceptions
    void editTest() throws CategoryWithTheSameNameExistsException {
        Category category = service.getCategoryById(1).get();
        category.setName("Category3");
        service.save(category);
        Assertions.assertEquals(service.getCategoryById(1).get().getName(), "Category3");
    }

    @Test // if we try to save category after editing with the existing name it must throw exception
    void editTestWithTheSameName() {
        Assertions.assertThrows(CategoryWithTheSameNameExistsException.class, () -> {
            Category category = service.getCategoryById(1).get();
            category.setName("Category1");
            service.save(category);
        });
    }

    @Test // if we try to save category without changes it must work without exceptions
    void editTestWithNoChanges() throws CategoryWithTheSameNameExistsException {
        Category category = service.getCategoryById(1).get();
        service.save(category);
    }

    @Test // if we try to delete category which id present in repository it must work without exceptions
    void delete() throws CategoryWithIdNotFound {
        Category category = new Category();
        category.setId(1);
        int initSize = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()).size();
        service.remove(category);
        int currentSize = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()).size();
        Assertions.assertEquals(initSize-1, currentSize);
    }

    @Test // if we try to delete category with not existed id it must throw exception
    void deleteWithNotExistedId() {
        Assertions.assertThrows(CategoryWithIdNotFound.class, () -> {
            Category category = new Category();
            category.setId(20);
            service.remove(category);
        });
    }

}
