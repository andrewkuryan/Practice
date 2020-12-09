package org.fekz115.task8.controller.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fekz115.task8.domain.Role;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.service.CartService;
import org.fekz115.task8.service.UserService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ViewController {

	private final UserService userService;
	private final CartService cartService;

	private final Logger logger = LogManager.getLogger();

	public ViewController(UserService userService, CartService cartService) {
		this.userService = userService;
		this.cartService = cartService;
	}

	@GetMapping({"", "index"})
	public ModelAndView index(Model model) {
		return new ModelAndView("redirect:/product");
	}

	@GetMapping("registration")
	public String registration(Model model) {
		model.addAttribute("page", "registration");
		return "common_page";
	}

	@PostMapping("registration")
	public String postRegistration(
			User user,
			Model model
	) {
		if (user.getEmail().isEmpty() || user.getEmail().isBlank()) {
			user.setEmail(null);
		}
		try {
			userService.register(user);
			cartService.create(user);
			model.addAttribute("message", "Registration Successful");
			model.addAttribute("page", "login");
		} catch (ServiceException e) {
			model.addAttribute("exception", e);
			model.addAttribute("page", "registration");
		}
		return "common_page";
	}

	@GetMapping("login")
	public String login(Model model) {
		model.addAttribute("page", "login");
		return "common_page";
	}

	@GetMapping("user")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String users(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("roles", Arrays.asList(Role.values()));
		model.addAttribute("page", "user_table");
		return "common_page";
	}

	@GetMapping("/user/remove/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String removeUser(Model model, @PathVariable Integer id) {
		userService.remove(id);
		return users(model);
	}

	@GetMapping("/user/save/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String saveUser(Model model, @PathVariable Integer id, @RequestParam Map<String, String> roles) {
		var optionalUser = userService.getUserById(id);
		optionalUser.ifPresent(user -> {
			user.setRoles(roles.keySet().stream().map(Role::valueOf).collect(Collectors.toSet()));
			userService.save(user);
		});
		return users(model);
	}

}
