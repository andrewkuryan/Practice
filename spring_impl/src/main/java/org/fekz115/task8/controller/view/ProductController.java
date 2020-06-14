package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Category;
import org.fekz115.task8.domain.Product;
import org.fekz115.task8.domain.ProductSpecification;
import org.fekz115.task8.domain.Specification;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.PhotosService;
import org.fekz115.task8.service.ProductService;
import org.fekz115.task8.service.ProductSpecificationsService;
import org.fekz115.task8.service.exception.ServiceException;
import org.fekz115.task8.util.Utils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProductSpecificationsService productSpecificationsService;
    private final PhotosService photosService;

    public ProductController(CategoryService categoryService, ProductService productService, ProductSpecificationsService productSpecificationsService, PhotosService photosService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.productSpecificationsService = productSpecificationsService;
        this.photosService = photosService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("page", "catalog");
        model.addAttribute("catalog", true);
        return "common_page";
    }

    @GetMapping({"/create/{categoryId}", "/edit/{id}"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createGet(@PathVariable(required = false) Integer id, @PathVariable(required = false) Integer categoryId, Model model) {
        Product product;
        Category category;
        if(id == null) {
            category = categoryService.getCategoryById(categoryId).get();
            product = new Product();
            product.setCategory(category);
        } else {
            Product t = new Product();
            t.setId(id);
            product = productService.getProduct(t).get();
            category = product.getCategory();
        }
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("page", "create_product");
        return "common_page";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createPost(
            @RequestParam("images") MultipartFile[] photos,
            Product product,
            Integer categoryId,
            @RequestParam Map<String, String> map,
            Model model
    ) {
        Category category = categoryService.getCategoryById(categoryId).get();
        product.setCategory(category);
        try {
            productService.save(product);
            Utils.filterOnlyIntegerKeys(map)
                    .entrySet()
                    .stream()
                    .map(x -> {
                        Specification specification = new Specification();
                        specification.setId(x.getKey());
                        ProductSpecification productSpecification = new ProductSpecification();
                        ProductSpecification.PrimaryKey pk = new ProductSpecification.PrimaryKey();
                        pk.setProductId(product.getId());
                        pk.setSpecificationId(specification.getId());
                        productSpecification.setPrimaryKey(pk);
                        productSpecification.setProduct(product);
                        productSpecification.setSpecification(specification);
                        productSpecification.setValue(x.getValue());
                        return productSpecification;
                    }).forEach(productSpecification -> {
                        try {
                            productSpecificationsService.save(productSpecification);
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                    });
            if(photos != null && photos.length != 0) {
                Arrays.stream(photos)
                        .forEach(x -> photosService.save(x, product));
            }
            model.addAttribute("message", "Created successfully");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return getAll(model);
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String remove(@PathVariable Integer id, Model model) {
        try {
            Product product = new Product();
            product.setId(id);
            productService.remove(product);
            model.addAttribute("message", "Successfully removed");
        } catch (ServiceException e) {
            model.addAttribute("exception", e.getMessage());
        }
        return getAll(model);
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable Integer id, Model model) {
        Product product = new Product();
        product.setId(id);
        product = productService.getProduct(product).get();
        model.addAttribute("product", product);
        model.addAttribute("page", "product");
        return "common_page";
    }

}
