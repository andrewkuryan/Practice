package org.fekz115.task8.util;

import org.fekz115.task8.domain.Product;
import org.fekz115.task8.domain.ProductSpecification;
import org.fekz115.task8.domain.Specification;

import java.util.*;
import java.util.stream.Collectors;

public class Utils {

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
        if(productSpecifications != null) {
            productSpecification = productSpecifications
                    .stream()
                    .filter(x -> x.getSpecification().getId() == specification.getId())
                    .findFirst()
                    .orElse(new ProductSpecification());
        }
        return productSpecification.getValue() == null ? "" : productSpecification.getValue();
    }

}
