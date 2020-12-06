package org.fekz115.task8.controller.view;

import org.fekz115.task8.domain.Specification;
import org.fekz115.task8.service.SpecificationService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/specification")
public class SpecificationController {

	private final SpecificationService specificationService;

	public SpecificationController(SpecificationService specificationService) {
		this.specificationService = specificationService;
	}

	@GetMapping
	public String all(Model model) {
		model.addAttribute("specifications", specificationService.getSpecifications());
		model.addAttribute("page", "specifications");
		return "common_page";
	}


	@GetMapping({"/create", "/edit/{id}"})
	public String createSpecificationGet(@PathVariable Optional<Integer> id, Model model) {
		Specification specification;
		if (id.isEmpty()) {
			specification = new Specification();
		} else {
			specification = specificationService.getById(id.get()).get();
		}
		model.addAttribute("specification", specification);
		model.addAttribute("page", "create_specification");
		return "common_page";
	}

	@PostMapping
	public String createSpecification(Specification specification, Model model) {
		try {
			specificationService.save(specification);
			model.addAttribute("message", "Specification " + specification.getName() + " successfully created");
		} catch (ServiceException e) {
			model.addAttribute("exception", e);
		}
		return all(model);
	}

	@GetMapping("/remove/{id}")
	public String remove(@PathVariable Integer id, Model model) {
		try {
			Specification specification = new Specification();
			specification.setId(id);
			specificationService.remove(specification);
			model.addAttribute("message", "Successfully removed");
		} catch (ServiceException e) {
			model.addAttribute("exception", e.getMessage());
		}
		return all(model);
	}

}
