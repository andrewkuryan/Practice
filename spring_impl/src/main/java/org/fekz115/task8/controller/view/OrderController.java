package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Office;
import org.fekz115.task8.domain.Order;
import org.fekz115.task8.domain.Product;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.service.OfficeService;
import org.fekz115.task8.service.OrderService;
import org.fekz115.task8.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OfficeService officeService;

    public OrderController(OrderService orderService, UserService userService, OfficeService officeService) {
        this.orderService = orderService;
        this.userService = userService;
        this.officeService = officeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER')")
    public String all(Model model) {
        model.addAttribute("orders", orderService.getAll());
        model.addAttribute("page", "orders");
        return "common_page";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("offices", officeService.getAll());
        model.addAttribute("page", "cart");
        return "common_page";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public String createOrder(
            Integer officeId,
            @RequestParam(value = "products[]") List<Integer> products,
            Principal principal,
            Model model
    ) {
        Optional<User> user = userService.getUserByLogin(principal.getName());
        if (user.isPresent()) {
            Office office = new Office();
            office.setId(officeId);
            products.stream()
                    .map(x -> {
                        Product p = new Product();
                        p.setId(x);
                        return p;
                    }).forEach(x -> {
                Order order = new Order();
                order.setOffice(office);
                order.setOrderDate(new Date(new java.util.Date().getTime()));
                order.setUser(user.get());
                order.setProduct(x);
                order.setReceived(false);
                orderService.save(order);
            });
            model.addAttribute("orderedSuccessful", true);
            model.addAttribute("message", "Ordered successfully");
        } else {
            model.addAttribute("exception", "Ordering error: user not found");
        }
        model.addAttribute("page", "index");
        return "common_page";
    }

    @GetMapping("/mark/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String markReceived(@PathVariable Integer id, Model model) {
        orderService.setReceived(id);
        return all(model);
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String remove(@PathVariable Integer id, Model model) {
        orderService.remove(id);
        return all(model);
    }

}
