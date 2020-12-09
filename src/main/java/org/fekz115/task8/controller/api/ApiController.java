package org.fekz115.task8.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fekz115.task8.domain.CityDeliveryArea;
import org.fekz115.task8.domain.DeliveryArea;
import org.fekz115.task8.domain.Store;
import org.fekz115.task8.service.DeliveryAreaService;
import org.fekz115.task8.service.ProductService;
import org.fekz115.task8.service.UserService;
import org.fekz115.task8.util.Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class DeliveryInfo {

	private String estimatedTime;
	private List<ProductDeliveryInfo> productsDeliveryInfo;

	public DeliveryInfo(String estimatedTime, List<ProductDeliveryInfo> productsDeliveryInfo) {
		this.estimatedTime = estimatedTime;
		this.productsDeliveryInfo = productsDeliveryInfo;
	}
}

class ProductDeliveryInfo {

	private int productId;
	private String status;
	private final Time estimatedTime;
	private final int deliveryAreaId;

	public ProductDeliveryInfo(int productId, String status, Time estimatedTime, int deliveryAreaId) {
		this.productId = productId;
		this.status = status;
		this.estimatedTime = estimatedTime;
		this.deliveryAreaId = deliveryAreaId;
	}

	public Time getEstimatedTime() {
		return estimatedTime;
	}

	public int getDeliveryAreaId() {
		return deliveryAreaId;
	}
}

@RestController
@RequestMapping("/api")
public class ApiController {

	private final UserService userService;
	private final DeliveryAreaService deliveryAreaService;
	private final ProductService productService;

	private final Logger logger = LogManager.getLogger();

	public ApiController(UserService userService, DeliveryAreaService deliveryAreaService, ProductService productService) {
		this.userService = userService;
		this.deliveryAreaService = deliveryAreaService;
		this.productService = productService;
	}

	@PostMapping("/deliveryInfo")
	public DeliveryInfo getDeliveryInfo(
			@RequestParam("productIds") int[] productIds,
			@RequestParam("cityId") int cityId
	) {
		Optional<CityDeliveryArea> cityDeliveryArea =
				deliveryAreaService.getCityById(cityId);

		if (cityDeliveryArea.isEmpty()) {
			return new DeliveryInfo("N/A", Collections.emptyList());
		}

		DeliveryArea deliveryArea = cityDeliveryArea.get().getDeliveryArea();
		Store deliveryAreaStore = deliveryArea.getStore();

		var productsDeliveryInfo = StreamSupport.stream(Arrays.spliterator(productIds), false)
				.map(productService::getById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(product -> {
					var productStores = product.getProductStores().stream()
							.filter(productStore -> productStore.getCount() > 0)
							.filter(productStore -> productStore.getStore().getDeliveryArea() != null)
							.collect(Collectors.toList());
					if (productStores.stream().findFirst().isEmpty()) {
						return new ProductDeliveryInfo(
								product.getId(),
								"Not available",
								null,
								-1
						);
					} else if (productStores.stream().anyMatch(productStore ->
							productStore.getStore().getId() == deliveryAreaStore.getId())) {
						return new ProductDeliveryInfo(
								product.getId(),
								"In stock",
								new Time(0),
								deliveryArea.getId()
						);
					} else {
						var fastedDeliveryArea = productStores.stream()
								.map(productStore -> productStore.getStore().getDeliveryArea())
								.min(Comparator.comparing(DeliveryArea::getEstimatedTime))
								.get();
						return new ProductDeliveryInfo(
								product.getId(),
								"In another delivery area",
								fastedDeliveryArea.getEstimatedTime(),
								fastedDeliveryArea.getId()
						);
					}
				})
				.collect(Collectors.toList());

		Time estimatedTime;
		if (productsDeliveryInfo.stream().anyMatch(productDeliveryInfo ->
				productDeliveryInfo.getEstimatedTime() == null)) {
			estimatedTime = null;
		} else {
			var longTime = deliveryArea.getEstimatedTime().getTime();
			longTime += productsDeliveryInfo.stream()
					.filter(productDeliveryInfo -> productDeliveryInfo.getEstimatedTime().getTime() > 0)
					.collect(Collectors.groupingBy(ProductDeliveryInfo::getDeliveryAreaId))
					.values()
					.stream()
					.map(productDeliveryInfos -> productDeliveryInfos.get(0))
					.mapToLong(productDeliveryInfo -> productDeliveryInfo.getEstimatedTime().getTime())
					.sum();
			estimatedTime = new Time(longTime);
		}

		return new DeliveryInfo(
				estimatedTime == null ? "N/A" : Utils.formatTime(estimatedTime),
				productsDeliveryInfo
		);
	}
}
