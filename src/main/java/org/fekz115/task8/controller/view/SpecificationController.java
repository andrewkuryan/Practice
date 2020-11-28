package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.domain.Specification;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.SpecificationService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/specification")
public class SpecificationController {

    private final CategoryService categoryService;
    private final SpecificationService specificationService;

    public SpecificationController(CategoryService categoryService, SpecificationService specificationService) {
        this.categoryService = categoryService;
        this.specificationService = specificationService;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("page", "specifications");
        return "common_page";
    }


    @GetMapping({"/create", "/edit/{id}"})
    public String createSpecificationGet(@PathVariable Optional<Integer> id, Model model) {
        Specification specification;
        if(id.isEmpty()) {
            specification = new Specification();
        } else {
            specification = specificationService.getById(id.get()).get();
        }
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("specification", specification);
        model.addAttribute("page", "create_specification");
        return "common_page";
    }

    @PostMapping
    public String createSpecification(Specification specification, @RequestParam int categoryId, Model model) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        try {
            specificationService.save(specification);
            model.addAttribute("message", "Specification " + specification.getName() + " successfully created for category " + category.get().getName());
        } catch (ServiceException e) {
            model.addAttribute("exception", e);
        }
        return all(model);
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Integer id, Model model) {
        try {
            Specification specification = new Specification();
            specification.setId(id);
            specificationService.remove(specification);
            model.addAttribute("message", "Successfully removed");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return all(model);
    }

}
