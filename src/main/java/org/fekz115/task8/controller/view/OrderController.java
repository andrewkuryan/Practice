package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.*;
import org.fekz115.task8.service.CartService;
import org.fekz115.task8.service.DeliveryAreaService;
import org.fekz115.task8.service.OrderService;
import org.fekz115.task8.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;
	private final DeliveryAreaService deliveryAreaService;
	private final UserService userService;

	public OrderController(
			OrderService orderService,
			CartService cartService,
			DeliveryAreaService deliveryAreaService,
			UserService userService
	) {
		this.orderService = orderService;
		this.cartService = cartService;
		this.deliveryAreaService = deliveryAreaService;
		this.userService = userService;
	}

	@PostMapping
	public ModelAndView createOrder(
			Order order,
			Integer cartId,
			Integer deliveryCityId,
			Principal principal,
			@RequestParam Map<String, String> map,
			Model model
	) {
		try {
			Cart cart = cartService.getById(cartId).get();
			CityDeliveryArea cityDeliveryArea = deliveryAreaService.getCityById(deliveryCityId).get();

			order.setStatus("Pending");
			order.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
			order.setCart(cart);
			order.setCityDeliveryArea(cityDeliveryArea);

			if (order.getId() == 0) {
				orderService.save(order, Collections.emptyList());
			}

			var orderProductStores = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("product-"))
					.map(entry -> {
						int productId = Integer.parseInt(entry.getKey().replaceFirst("product-", ""));
						int deliveryAreaId = Integer.parseInt(entry.getValue());

						Store store = deliveryAreaService.getById(deliveryAreaId).get().getStore();

						OrderProductStore orderProductStore = new OrderProductStore();
						OrderProductStore.PrimaryKey pk = new OrderProductStore.PrimaryKey();
						pk.setOrderId(order.getId());
						pk.setProductId(productId);
						pk.setStoreId(store.getId());
						orderProductStore.setPrimaryKey(pk);
						orderProductStore.setCount(1);

						return orderProductStore;
					})
					.collect(Collectors.toList());

			orderService.save(order, orderProductStores);
			cartService.create(userService.getUserByLogin(principal.getName()).get());
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return new ModelAndView("redirect:/order");
	}

	@GetMapping
	public String all(Principal principal, Model model) {
		User user = userService.getUserByLogin(principal.getName()).get();
		if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.MANAGER)) {
			model.addAttribute("orders", orderService.getAll());
		} else if (user.getRoles().contains(Role.USER)) {
			model.addAttribute("orders", orderService.getUsers(user));
		}

		model.addAttribute("page", "orders");
		return "common_page";
	}

	@GetMapping("/{id}/cancel")
	public ModelAndView cancel(@PathVariable Integer id, Model model) {
		try {
			orderService.setStatus(id, "Canceled");
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return new ModelAndView("redirect:/order");
	}

	@PostMapping("/{id}/status")
	public ModelAndView changeStatus(
			@PathVariable Integer id,
			@RequestParam("status") String status,
			Model model
	) {
		try {
			orderService.setStatus(id, status);
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return new ModelAndView("redirect:/order");
	}

	/*@PutMapping("{id}/status")
	@PreAuthorize("hasAuthority('MANAGER')")
	public String changeStatus(
			@PathVariable Integer id,
			@RequestParam(value = "status") String status,
			Model model
	) {
		orderService.setStatus(id, status);
		return all(model);
	}*/
}
