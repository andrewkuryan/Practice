package org.fekz115.task8.controller.view;

import com.google.gson.Gson;
import org.fekz115.task8.domain.CityDeliveryArea;
import org.fekz115.task8.domain.DeliveryArea;
import org.fekz115.task8.domain.Store;
import org.fekz115.task8.service.CityService;
import org.fekz115.task8.service.DeliveryAreaService;
import org.fekz115.task8.service.StoreService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class CityInfo {

	private int id;
	private String name;


	CityInfo(int id, String name) {
		this.id = id;
		this.name = name;
	}
}

@Controller
@RequestMapping("/deliveryArea")
public class DeliveryAreaController {

	private final DeliveryAreaService deliveryAreaService;
	private final CityService cityService;
	private final StoreService storeService;
	private final Gson gson;

	public DeliveryAreaController(DeliveryAreaService deliveryAreaService, CityService cityService, StoreService storeService) {
		this.deliveryAreaService = deliveryAreaService;
		this.cityService = cityService;
		this.storeService = storeService;
		this.gson = new Gson();
	}

	@GetMapping
	public String all(Model model) {
		model.addAttribute("deliveryAreas", deliveryAreaService.getDeliveryAreas());
		model.addAttribute("page", "deliveryAreas");
		return "common_page";
	}

	@GetMapping({"/edit/{id}", "/create"})
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createGet(@PathVariable(required = false) Integer id, Model model) {
		DeliveryArea deliveryArea = deliveryAreaService.getById(id).orElse(new DeliveryArea());

		long days = 0;
		long hours = 0;
		long minutes = 0;
		if (deliveryArea.getEstimatedTime() != null) {
			var localTime = deliveryArea.getEstimatedTime().getTime();
			days = localTime / (24 * 60 * 60 * 1000);
			localTime = localTime % (24 * 60 * 60 * 1000);

			hours = localTime / (60 * 60 * 1000);
			localTime = localTime % (60 * 60 * 1000);

			minutes = localTime / (60 * 1000);
		}

		var cities = cityService.getCities();
		var stores = StreamSupport.stream(cities.spliterator(), false)
				.flatMap(city -> city.getStores().stream())
				.collect(Collectors.toList());

		var cityInfo = StreamSupport.stream(cities.spliterator(), false)
				.map(city -> new CityInfo(city.getId(), city.getName()))
				.map(gson::toJson)
				.collect(Collectors.toList());

		model.addAttribute("deliveryArea", deliveryArea);
		model.addAttribute("estimatedDays", days);
		model.addAttribute("estimatedHours", hours);
		model.addAttribute("estimatedMinutes", minutes);
		model.addAttribute("stores", stores);
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("page", "create_deliveryArea");
		return "common_page";
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createPost(
			DeliveryArea deliveryArea,
			@RequestParam("storeId") String storeId,
			@RequestParam("estimatedDays") String estimatedDays,
			@RequestParam("estimatedHours") String estimatedHours,
			@RequestParam("estimatedMinutes") String estimatedMinutes,
			@RequestParam Map<String, String> map,
			Model model
	) {
		try {
			Store store = storeService.getById(Integer.parseInt(storeId)).get();
			deliveryArea.setStore(store);

			Time estimatedTime = new Time(
					Integer.parseInt(estimatedMinutes) * 60 * 1000 +
							Integer.parseInt(estimatedHours) * 60 * 60 * 1000 +
							Integer.parseInt(estimatedDays) * 24 * 60 * 60 * 100
			);
			deliveryArea.setEstimatedTime(estimatedTime);

			if (deliveryArea.getId() == 0) {
				deliveryAreaService.save(deliveryArea, Collections.emptyList(), Collections.emptyList());
			}

			System.out.println(map);

			var newCities = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("newCity-"))
					.filter(entry -> !entry.getValue().isEmpty() && !entry.getValue().isBlank())
					.map(entry -> {
						int cityId = Integer.parseInt(entry.getValue());

						CityDeliveryArea cityDeliveryArea = new CityDeliveryArea();
						cityDeliveryArea.setDeliveryArea(deliveryArea);
						cityService.getCityById(cityId).ifPresent(cityDeliveryArea::setCity);

						return cityDeliveryArea;
					})
					.collect(Collectors.toList());

			var oldCities = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("city-"))
					.filter(entry -> !entry.getValue().isEmpty() && !entry.getValue().isBlank())
					.map(entry -> Integer.parseInt(entry.getValue()))
					.collect(Collectors.toList());

			deliveryAreaService.save(deliveryArea, newCities, oldCities);
			model.addAttribute("message", "Delivery area " + deliveryArea.getName() + " successfully saved");
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return all(model);
	}
}
