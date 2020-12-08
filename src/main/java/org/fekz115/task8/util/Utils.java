package org.fekz115.task8.util;

import org.fekz115.task8.domain.Product;
import org.fekz115.task8.domain.ProductSpecification;
import org.fekz115.task8.domain.Specification;

import java.sql.Time;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

	public static long estimatedDays(Time time) {
		return time.getTime() / (24 * 60 * 60 * 1000);
	}

	public static long estimatedHours(Time time) {
		return (time.getTime() % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
	}

	public static long estimatedMinutes(Time time) {
		return ((time.getTime() % (24 * 60 * 60 * 1000)) % (60 * 60 * 1000)) / (60 * 1000);
	}

	public static String formatTime(Time time) {
		long days = estimatedDays(time);
		long hours = estimatedHours(time);
		long minutes = estimatedMinutes(time);
		String result = "";
		if (days != 0) {
			result += days + (days == 1 ? " Day" : " Days") + " ";
		}
		if (hours != 0) {
			result += hours + (hours == 1 ? " Hour" : " Hours") + " ";
		}
		if (minutes != 0) {
			result += minutes + (minutes == 1 ? " Minute" : " Minutes");
		}
		return result.trim();
	}

	public static <T> Map<Integer, T> filterOnlyIntegerKeys(Map<String, T> map) {
		return map
				.entrySet()
				.stream()
				.filter(x -> {
					try {
						Integer.parseInt(x.getKey());
						return true;
					} catch (NumberFormatException e) {
						return false;
					}
				})
				.map(x -> new AbstractMap.SimpleEntry<>(Integer.parseInt(x.getKey()), x.getValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public static String getSpecificationValue(Product product, Specification specification) {
		Set<ProductSpecification> productSpecifications = product.getSpecifications();
		ProductSpecification productSpecification = new ProductSpecification();
		if (productSpecifications != null) {
			productSpecification = productSpecifications
					.stream()
					.filter(x -> x.getSpecification().getId() == specification.getId())
					.findFirst()
					.orElse(new ProductSpecification());
		}
		return productSpecification.getValue() == null ? "" : productSpecification.getValue();
	}

}
