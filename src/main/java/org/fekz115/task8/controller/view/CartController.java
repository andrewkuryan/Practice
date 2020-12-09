package org.fekz115.task8.controller.view;

import com.google.gson.Gson;
import org.fekz115.task8.domain.Cart;
import org.fekz115.task8.domain.Order;
import org.fekz115.task8.service.CartService;
import org.fekz115.task8.service.DeliveryAreaService;
import org.fekz115.task8.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class CartProductInfo {

	private int id;

	public CartProductInfo(int id) {
		this.id = id;
	}
}

@Controller
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final UserService userService;
	private final DeliveryAreaService deliveryAreaService;
	private final Gson gson;

	public CartController(CartService cartService, UserService userService, DeliveryAreaService deliveryAreaService) {
		this.cartService = cartService;
		this.userService = userService;
		this.deliveryAreaService = deliveryAreaService;
		this.gson = new Gson();
	}

	@GetMapping
	public String userCart(Principal principal, Model model) {
		Optional<Cart> cart = cartService
				.getActiveCart(userService.getUserByLogin(principal.getName()));

		if (cart.isPresent()) {
			Order order = new Order();

			var deliveryCities = StreamSupport.stream(deliveryAreaService.getDeliveryAreas().spliterator(), false)
					.flatMap(deliveryArea -> deliveryArea.getCityDeliveryAreas().stream())
					.collect(Collectors.toList());

			var cartProductsInfo = cart.get().getCartProducts().stream()
					.map(cartProduct -> new CartProductInfo(cartProduct.getProduct().getId()))
					.map(gson::toJson)
					.collect(Collectors.toList());

			model.addAttribute("cart", cart.get());
			model.addAttribute("cartProductsInfo", cartProductsInfo);
			model.addAttribute("deliveryCities", deliveryCities);
			model.addAttribute("order", order);
		} else {
			model.addAttribute("exception", "Cart error: cart not found");
		}

		model.addAttribute("page", "cart");
		return "common_page";
	}

	@GetMapping("/remove/{productId}")
	public ModelAndView removeCartItem(@PathVariable Integer productId, Principal principal, Model model) {
		try {
			Optional<Cart> cart = cartService
					.getActiveCart(userService.getUserByLogin(principal.getName()));

			if (cart.isPresent()) {
				var removedCartProducts = cart.get().getCartProducts().stream()
						.filter(cartProduct -> cartProduct.getProduct().getId() == productId)
						.collect(Collectors.toList());

				cartService.save(Collections.emptyList(), removedCartProducts);
			}
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return new ModelAndView("redirect:/cart");
	}
}
