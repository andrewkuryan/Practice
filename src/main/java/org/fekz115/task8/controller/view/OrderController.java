package org.fekz115.task8.controller.view;

import org.fekz115.task8.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
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
		model.addAttribute("page", "cart");
		return "common_page";
	}

	@PutMapping("{id}/status")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String changeStatus(
			@PathVariable Integer id,
			@RequestParam(value = "status") String status,
			Model model
	) {
		orderService.setStatus(id, status);
		return all(model);
	}

	@GetMapping("/remove/{id}")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String remove(@PathVariable Integer id, Model model) {
		orderService.remove(id);
		return all(model);
	}

}
