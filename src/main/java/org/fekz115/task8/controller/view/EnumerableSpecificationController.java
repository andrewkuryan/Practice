package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.EnumerableSpecification;
import org.fekz115.task8.domain.EnumerableValue;
import org.fekz115.task8.service.EnumerableSpecificationService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/enumerableSpecification")
public class EnumerableSpecificationController {

	private final EnumerableSpecificationService specificationService;

	public EnumerableSpecificationController(EnumerableSpecificationService specificationService) {
		this.specificationService = specificationService;
	}

	@GetMapping
	public String all(Model model) {
		model.addAttribute("specifications", specificationService.getSpecifications());
		model.addAttribute("page", "enumerableSpecifications");
		return "common_page";
	}

	@GetMapping({"/create", "/edit/{id}"})
	public String createSpecificationGet(@PathVariable Optional<Integer> id, Model model) {
		EnumerableSpecification specification;
		if (id.isEmpty()) {
			specification = new EnumerableSpecification();
		} else {
			specification = specificationService.getById(id.get()).get();
		}

		model.addAttribute("specification", specification);
		model.addAttribute("page", "create_enumerableSpecification");
		return "common_page";
	}

	@PostMapping
	public String createSpecification(
			EnumerableSpecification specification,
			@RequestParam Map<String, String> map,
			Model model
	) {
		try {
			if (specification.getId() == 0) {
				specificationService.save(specification, Collections.emptyList(), Collections.emptyList());
			}

			var newValues = map.entrySet().stream()
					.filter(entry -> entry.getKey().startsWith("newEnumerableValue-"))
					.filter(entry -> !entry.getValue().isEmpty() && !entry.getValue().isBlank())
					.map(entry -> {
						var newValue = new EnumerableValue();
						newValue.setValue(entry.getValue());
						newValue.setEnumerableSpecification(specification);
						return newValue;
					})
					.collect(Collectors.toList());

			var oldValues = map.keySet().stream()
					.filter(key -> key.startsWith("enumerableValue-"))
					.map(key -> Integer.parseInt(key.replaceFirst("enumerableValue-", "")))
					.collect(Collectors.toList());

			specificationService.save(specification, newValues, oldValues);

			model.addAttribute("message", "Enumerable specification " + specification.getName() + " successfully created");
		} catch (ServiceException e) {
			model.addAttribute("exception", e);
		}
		return all(model);
	}
}
