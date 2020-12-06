package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.City;
import org.fekz115.task8.domain.Coords;
import org.fekz115.task8.service.CityService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/city")
public class CityController {

	private final CityService cityService;

	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping
	public String all(Model model) {
		model.addAttribute("cities", cityService.getCities());
		model.addAttribute("page", "cities");
		return "common_page";
	}

	@GetMapping({"/edit/{id}", "/create"})
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createGet(@PathVariable(required = false) Integer id, Model model) {
		City city = cityService.getCityById(id).orElse(new City());
		model.addAttribute("city", city);
		model.addAttribute("page", "create_city");
		return "common_page";
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createPost(
			City city,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			Model model
	) {
		try {
			Coords coords = new Coords();
			coords.setLatitude(Double.parseDouble(latitude));
			coords.setLongitude(Double.parseDouble(longitude));

			cityService.save(city, coords);
			model.addAttribute("message", "City " + city.getName() + " successfully saved");
		} catch (ServiceException e) {
			model.addAttribute("exception", e.getMessage());
		}
		return all(model);
	}

	@GetMapping("/remove/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String remove(@PathVariable int id, Model model) {
		try {
			City city = new City();
			city.setId(id);
			cityService.remove(city);
			model.addAttribute("message", "Successfully removed");
		} catch (ServiceException e) {
			model.addAttribute("exception", e.getMessage());
		}
		return all(model);
	}
}
