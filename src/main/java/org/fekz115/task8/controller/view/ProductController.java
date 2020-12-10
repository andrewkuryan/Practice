package org.fekz115.task8.controller.view;

import com.google.gson.Gson;
import org.fekz115.task8.domain.*;
import org.fekz115.task8.service.*;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class ProductStoreInfo {

	private double latitude;
	private double longitude;
	private String address;
	private int count;

	ProductStoreInfo(double latitude, double longitude, String address, int count) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.count = count;
	}
}

class EnumerableValueInfo {

	private int id;
	private String value;

	EnumerableValueInfo(int id, String value) {
		this.id = id;
		this.value = value;
	}
}

class SpecificationInfo {

	private String type;
	private int id;
	private String name;
	private String unit;
	private EnumerableValueInfo[] values;

	SpecificationInfo(int id, String type, String name, String unit, EnumerableValueInfo[] values) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.unit = unit;
		this.values = values;
	}
}

@Controller
@RequestMapping("/product")
public class ProductController {

	private final CategoryService categoryService;
	private final ProductService productService;
	private final EnumerableValueService enumerableValueService;
	private final PhotosService photosService;
	private final SpecificationService specificationService;
	private final EnumerableSpecificationService enumerableSpecificationService;
	private final CartService cartService;
	private final UserService userService;
	private final Gson gson;

	public ProductController(
			CategoryService categoryService,
			ProductService productService,
			EnumerableValueService enumerableValueService,
			PhotosService photosService,
			SpecificationService specificationService,
			EnumerableSpecificationService enumerableSpecificationService,
			CartService cartService,
			UserService userService
	) {
		this.categoryService = categoryService;
		this.productService = productService;
		this.enumerableValueService = enumerableValueService;
		this.photosService = photosService;
		this.specificationService = specificationService;
		this.enumerableSpecificationService = enumerableSpecificationService;
		this.cartService = cartService;
		this.userService = userService;
		this.gson = new Gson();
	}

	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("categories", categoryService.getCategories());
		model.addAttribute("page", "catalog");
		return "common_page";
	}

	@GetMapping({"/create/{categoryId}", "/edit/{id}"})
	@PreAuthorize("hasAuthority('ADMIN')")
	public String createGet(@PathVariable(required = false) Integer id, @PathVariable(required = false) Integer categoryId, Model model) {
		Product product;
		Category category;
		if (id == null) {
			category = categoryService.getCategoryById(categoryId).get();
			product = new Product();
			product.setCategory(category);
		} else {
			Product t = new Product();
			t.setId(id);
			product = productService.getProduct(t).get();
			category = product.getCategory();
		}

		var specifications = specificationService.getSpecifications();
		var enumerableSpecifications = enumerableSpecificationService.getSpecifications();
		var specificationsList = new ArrayList<SpecificationInfo>();
		specificationsList.addAll(StreamSupport.stream(specifications.spliterator(), false)
				.map(s -> new SpecificationInfo(s.getId(), "specification", s.getName(), s.getUnits(), null))
				.collect(Collectors.toList()));
		specificationsList.addAll(StreamSupport.stream(enumerableSpecifications.spliterator(), false)
				.map(s -> new SpecificationInfo(
						s.getId(), "enumerable", s.getName(), null,
						s.getEnumerableValues().stream().map(value -> new EnumerableValueInfo(
								value.getId(),
								value.getValue()
						)).toArray(EnumerableValueInfo[]::new)
				))
				.collect(Collectors.toList()));

		model.addAttribute("product", product);
		model.addAttribute("category", category);
		model.addAttribute("specificationsInfo", gson.toJson(specificationsList));
		model.addAttribute("specifications", specifications);
		model.addAttribute("enumerableSpecifications", enumerableSpecifications);
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
			if (product.getId() == 0) {
				productService.save(
						product,
						Collections.emptyList(), Collections.emptyList(),
						Collections.emptyList(), Collections.emptyList()
				);
			}

			var newSpecifications = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("newSpecification-"))
					.filter(entry -> entry.getValue().startsWith("specification-"))
					.map(entry -> {
						int index = Integer.parseInt(entry.getKey().replaceFirst("newSpecification-", ""));
						int specificationId = Integer.parseInt(entry.getValue().replaceFirst("specification-", ""));

						ProductSpecification productSpecification = new ProductSpecification();
						ProductSpecification.PrimaryKey pk = new ProductSpecification.PrimaryKey();
						pk.setProductId(product.getId());
						pk.setSpecificationId(specificationId);
						productSpecification.setPrimaryKey(pk);

						map.entrySet().stream()
								.filter(innerEntry -> innerEntry.getKey().startsWith("specificationValue-" + index))
								.findFirst()
								.ifPresent(valueEntry -> productSpecification.setValue(valueEntry.getValue()));

						return productSpecification;
					})
					.collect(Collectors.toList());

			var oldSpecifications = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("specification-"))
					.filter(entry -> entry.getValue().startsWith("specification-"))
					.map(entry -> Integer.parseInt(entry.getValue().replaceFirst("specification-", "")))
					.collect(Collectors.toList());

			var newEnumerables = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("newSpecification-"))
					.filter(entry -> entry.getValue().startsWith("enumerable-"))
					.map(entry -> {
						int index = Integer.parseInt(entry.getKey().replaceFirst("newSpecification-", ""));
						int enumerableId = Integer.parseInt(entry.getValue().replaceFirst("enumerable-", ""));
						EnumerableSpecification specification = new EnumerableSpecification();
						specification.setId(enumerableId);

						ProductEnumerableSpecification productSpecification = new ProductEnumerableSpecification();
						ProductEnumerableSpecification.PrimaryKey pk = new ProductEnumerableSpecification.PrimaryKey();

						pk.setProductId(product.getId());
						pk.setEnumerableSpecificationId(specification.getId());

						map.entrySet().stream()
								.filter(innerEntry -> innerEntry.getKey().startsWith("enumerableValue-" + index))
								.findFirst()
								.ifPresent(valueEntry ->
										pk.setEnumerableValueId(Integer.parseInt(valueEntry.getValue())));

						productSpecification.setPrimaryKey(pk);

						return productSpecification;
					})
					.collect(Collectors.toList());

			var oldEnumerables = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("specification-"))
					.filter(entry -> entry.getValue().startsWith("enumerable-"))
					.map(entry -> {
						ProductEnumerableSpecification.PrimaryKey pk = new ProductEnumerableSpecification.PrimaryKey();
						var index = Integer.parseInt(entry.getKey().replaceFirst("specification-", ""));
						var enumerableId = Integer.parseInt(entry.getValue().replaceFirst("enumerable-", ""));
						pk.setProductId(product.getId());
						pk.setEnumerableSpecificationId(enumerableId);
						map.entrySet().stream()
								.filter(innerEntry -> innerEntry.getKey().startsWith("enumerableValue-" + index))
								.findFirst()
								.map(innerEntry -> Integer.parseInt(innerEntry.getValue()))
								.ifPresent(pk::setEnumerableValueId);
						return pk;
					})
					.collect(Collectors.toList());

			productService.save(
					product,
					newSpecifications, oldSpecifications,
					newEnumerables, oldEnumerables
			);

			if (photos != null && photos.length != 0) {
				Arrays.stream(photos)
						.filter(multipartFile ->
								multipartFile.getOriginalFilename() != null &&
										!multipartFile.getOriginalFilename().isEmpty() &&
										!multipartFile.getOriginalFilename().isBlank())
						.forEach(x -> photosService.save(x, product));
			}
			model.addAttribute("message", "Created successfully");
		} catch (Exception e) {
			System.out.println(e);
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
	public String getProductPage(@PathVariable Integer id, Principal principal, Model model) {
		Product product = new Product();
		product.setId(id);
		product = productService.getProduct(product).get();

		var nonEmptyStores = product.getProductStores().stream()
				.filter(store -> store.getCount() > 0)
				.collect(Collectors.toList());

		var nonEmptyStoresInfo = nonEmptyStores.stream()
				.map(store -> new ProductStoreInfo(
						store.getStore().getCoords().getLatitude(),
						store.getStore().getCoords().getLongitude(),
						store.getStore().getAddress(),
						store.getCount()
				))
				.map(gson::toJson)
				.collect(Collectors.toList());

		if (principal != null) {
			cartService.getActiveCart(userService.getUserByLogin(principal.getName()))
					.ifPresent(cart -> {
						var isInCart = cart.getCartProducts().stream()
								.anyMatch(cartProduct -> cartProduct.getProduct().getId() == id);
						model.addAttribute("isInCart", isInCart);
					});
		} else {
			model.addAttribute("isInCart", false);
		}

		model.addAttribute("product", product);
		model.addAttribute("nonEmptyStores", nonEmptyStores);
		model.addAttribute("nonEmptyStoresInfo", nonEmptyStoresInfo);
		model.addAttribute("page", "product");
		return "common_page";
	}

	@GetMapping("/{id}/cart")
	public ModelAndView addToCart(@PathVariable Integer id, Principal principal, Model model) {
		Optional<Cart> cart = cartService
				.getActiveCart(userService.getUserByLogin(principal.getName()));

		CartProduct cartProduct = new CartProduct();
		CartProduct.PrimaryKey pk = new CartProduct.PrimaryKey();
		pk.setProductId(id);
		pk.setCartId(cart.get().getId());
		cartProduct.setPrimaryKey(pk);
		cartProduct.setCount(1);

		cartService.save(Collections.singletonList(cartProduct), Collections.emptyList());

		return new ModelAndView("redirect:/product/" + id);
	}
}
