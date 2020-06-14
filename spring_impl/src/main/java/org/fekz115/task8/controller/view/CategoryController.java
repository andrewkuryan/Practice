package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("page", "categories");
        return "common_page";
    }

    @GetMapping("/{id}")
    public String listProducts(@PathVariable Integer id, Model model) {
        Category category = categoryService.getCategoryById(id).get();
        model.addAttribute("category", category);
        model.addAttribute("page", "products");
        model.addAttribute("catalog", true);
        return "common_page";
    }

    @GetMapping({"/create", "/edit/{category}"})
     @PreAuthorize("hasAuthority('ADMIN')")
    public String createGet(@PathVariable(name = "category") Optional<String> optionalCategory, Model model) {
        Category category;
        if(optionalCategory.isEmpty()) {
            category = new Category();
        } else {
            category = categoryService.getCategoryById(Integer.parseInt(optionalCategory.get())).get();
        }
        model.addAttribute("category", category);
        model.addAttribute("page", "create_category");
        return "common_page";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createPost(Category category, Model model) {
        try {
            categoryService.save(category);
            model.addAttribute("message", "Category " + category.getName() + " successfully saved");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return all(model);
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String remove(@PathVariable String id, Model model) {
        Category category = new Category();
        category.setId(Integer.parseInt(id));
        try {
            categoryService.remove(category);
            model.addAttribute("message", "Category successfully removed");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return all(model);
    }

}
