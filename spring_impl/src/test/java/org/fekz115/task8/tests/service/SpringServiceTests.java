package org.fekz115.task8.tests.service;

import org.fekz115.task8.config.MvcConfig;
import org.fekz115.task8.domain.Category;
import org.fekz115.task8.domain.Specification;
import org.fekz115.task8.repository.CategoryRepository;
import org.fekz115.task8.repository.SpecificationRepository;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.SpecificationService;
import org.fekz115.task8.service.exception.category.CategoryWithIdNotFound;
import org.fekz115.task8.service.exception.category.CategoryWithTheSameNameExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringJUnitWebConfig(MvcConfig.class)
public class SpringServiceTests {

    @Autowired
    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @Autowired
    SpecificationRepository specificationRepository;
    SpecificationService specificationService;

    @Test
    public void creationBeansTest() {
        Assertions.assertNotNull(categoryRepository);
        Assertions.assertNotNull(categoryService);
    }

    @BeforeEach
    public void init() {
        categoryService = new CategoryService(categoryRepository);
        Category category = new Category();
        category.setName("Category1");
        try {
            categoryService.save(category);
        } catch (Exception e) { }
        category = new Category();
        category.setName("Category2");
        try {
            categoryService.save(category);
        } catch (Exception e) { }

        specificationService = new SpecificationService(specificationRepository);
        Specification specification = new Specification();
        specification.setName("Specification1");
        specification.setCategory(category);
        try {
            specificationService.save(specification);
        } catch (Exception e) {}
    }

    @Test // if we try to save new category with the same name it must throw exception
    void saveWithTheSameNameTest() {
        Assertions.assertThrows(CategoryWithTheSameNameExistsException.class, () -> {
            Category category = new Category();
            category.setName("Category2");
            categoryService.save(category);
        });
    }

    @Test // if we edit category it must work without exceptions
    void editTest() throws CategoryWithTheSameNameExistsException {
        Category category = categoryService.getCategoryById(2).get();
        category.setName("Category3");
        categoryService.save(category);
        Assertions.assertEquals(categoryService.getCategoryById(2).get().getName(), "Category3");
    }

    @Test // if we try to save category after editing with the existing name it must throw exception
    void editTestWithTheSameName() {
        Assertions.assertThrows(CategoryWithTheSameNameExistsException.class, () -> {
            Category category = categoryService.getCategoryById(2).get();
            category.setName("Category1");
            categoryService.save(category);
        });
    }

    @Test // if we try to save category without changes it must work without exceptions
    void editTestWithNoChanges() throws CategoryWithTheSameNameExistsException {
        Category category = categoryService.getCategoryById(2).get();
        categoryService.save(category);
    }

    @Test // if we try to delete category which id present in repository it must work without exceptions
    void delete() throws CategoryWithIdNotFound {
        Category category = new Category();
        category.setId(1);
        int initSize = StreamSupport.stream(categoryRepository.findAll().spliterator(), false).collect(Collectors.toList()).size();
        categoryService.remove(category);
        int currentSize = StreamSupport.stream(categoryRepository.findAll().spliterator(), false).collect(Collectors.toList()).size();
        Assertions.assertEquals(initSize-1, currentSize);
    }

    @Test // if we try to delete category with not existed id it must throw exception
    void deleteWithNotExistedId() {
        Assertions.assertThrows(CategoryWithIdNotFound.class, () -> {
            Category category = new Category();
            category.setId(20);
            categoryService.remove(category);
        });
    }

}
