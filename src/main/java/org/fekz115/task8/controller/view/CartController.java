package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Cart;
import org.fekz115.task8.service.CartService;
import org.fekz115.task8.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final UserService userService;

	public CartController(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}

	@GetMapping
	public String userCart(Principal principal, Model model) {
		Optional<Cart> cart = cartService
				.getActiveCart(userService.getUserByLogin(principal.getName()));

		if (cart.isPresent()) {
			model.addAttribute("cart", cart.get());
		} else {
			model.addAttribute("exception", "Cart error: cart not found");
		}

		model.addAttribute("page", "cart");
		return "common_page";
	}
}
