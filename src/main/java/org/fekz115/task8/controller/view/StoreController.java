package org.fekz115.task8.controller.view;

import com.google.gson.Gson;
import org.fekz115.task8.domain.Coords;
import org.fekz115.task8.domain.ProductStore;
import org.fekz115.task8.domain.Store;
import org.fekz115.task8.service.CategoryService;
import org.fekz115.task8.service.CityService;
import org.fekz115.task8.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class StoreMapInfo {

	private final String address;
	private final String phone;
	private final Double latitude;
	private final Double longitude;

	public StoreMapInfo(String address, String phone, Double latitude, Double longitude) {
		this.address = address;
		this.phone = phone;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}

class ProductInfo {

	private int id;
	private String name;

	ProductInfo(int id, String name) {
		this.id = id;
		this.name = name;
	}
}

@Controller
@RequestMapping("/store")
public class StoreController {

	private final CityService cityService;
	private final StoreService storeService;
	private final CategoryService categoryService;
	private final Gson gson;

	public StoreController(CityService cityService, StoreService storeService, CategoryService categoryService) {
		this.cityService = cityService;
		this.storeService = storeService;
		this.categoryService = categoryService;
		this.gson = new Gson();
	}

	@GetMapping
	public String all(Model model) {
		var cities = StreamSupport.stream(cityService.getCities().spliterator(), false)
				.filter(city -> city.getStores().size() > 0)
				.collect(Collectors.toList());
		List<String> formattedStores = cities.stream()
				.flatMap(city -> city.getStores().stream()
						.map(store -> gson.toJson(new StoreMapInfo(
								store.getAddress(),
								store.getPhone(),
								store.getCoords().getLatitude(),
								store.getCoords().getLongitude()
						))))
				.collect(Collectors.toList());

		model.addAttribute("cities", cities);
		model.addAttribute("stores", formattedStores);
		model.addAttribute("page", "stores");
		return "common_page";
	}

	@GetMapping({"/edit/{id}", "/create"})
	public String createGet(@PathVariable(required = false) Integer id, Model model) {
		Store store;
		if (id == null) {
			store = new Store();
		} else {
			store = storeService.getById(id).orElse(new Store());
		}

		var products = StreamSupport.stream(categoryService.getCategories().spliterator(), false)
				.flatMap(category -> category.getProducts().stream())
				.collect(Collectors.toList());

		var productsInfo = products.stream()
				.map(product -> new ProductInfo(product.getId(), product.getName()))
				.map(gson::toJson)
				.collect(Collectors.toList());

		model.addAttribute("store", store);
		model.addAttribute("products", products);
		model.addAttribute("productsInfo", productsInfo);
		model.addAttribute("cities", cityService.getCities());
		model.addAttribute("page", "create_store");
		return "common_page";
	}

	@PostMapping
	public String createPost(
			Store store, int cityId,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,
			@RequestParam Map<String, String> map,
			Model model
	) {
		try {
			store.setCity(cityService.getCityById(cityId).get());
			Coords coords = new Coords();
			coords.setLatitude(Double.parseDouble(latitude));
			coords.setLongitude(Double.parseDouble(longitude));

			if (store.getId() == 0) {
				storeService.save(store, coords, Collections.emptyList(), Collections.emptyList());
			}

			var newProducts = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("newProduct-"))
					.filter(entry -> !entry.getValue().isEmpty() && !entry.getValue().isBlank())
					.map(entry -> {
						int index = Integer.parseInt(entry.getKey().replaceFirst("newProduct-", ""));
						int productId = Integer.parseInt(entry.getValue());

						ProductStore productStore = new ProductStore();
						ProductStore.PrimaryKey pk = new ProductStore.PrimaryKey();

						pk.setStoreId(store.getId());
						pk.setProductId(productId);
						productStore.setPrimaryKey(pk);

						map.entrySet().stream()
								.filter(innerEntry -> innerEntry.getKey().startsWith("newProductCount-" + index))
								.findFirst()
								.ifPresent(countEntry ->
										productStore.setCount(Integer.parseInt(countEntry.getValue())));

						return productStore;
					})
					.collect(Collectors.toList());

			var oldProducts = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("product-"))
					.filter(entry -> !entry.getValue().isEmpty() && !entry.getValue().isBlank())
					.map(entry -> Integer.parseInt(entry.getValue()))
					.collect(Collectors.toList());

			storeService.save(store, coords, newProducts, oldProducts);
			model.addAttribute("Store in " + store.getCity() + " with address " + store.getAddress() + " successfully created");
		} catch (Exception e) {
			model.addAttribute("exception", e.getMessage());
		}
		return all(model);
	}
}
