package org.fekz115.task8.service;

import org.fekz115.task8.domain.Specification;
import org.fekz115.task8.repository.SpecificationRepository;
import org.fekz115.task8.service.exception.specification.SpecificationWithTheSameNameAlreadyExistsException;
import org.fekz115.task8.service.exception.specification.SpecificationWithThisIdNotFound;

import java.util.Optional;

public class SpecificationService {

	protected final SpecificationRepository repository;

	public SpecificationService(SpecificationRepository repository) {
		this.repository = repository;
	}

	public void save(Specification specification) throws SpecificationWithTheSameNameAlreadyExistsException {
		if (repository.existsByNameAndIdNot(specification.getName(), specification.getId())) {
			throw new SpecificationWithTheSameNameAlreadyExistsException();
		} else {
			var entity = repository.findById(specification.getId()).orElse(specification);
			entity.setName(specification.getName());
			entity.setUnits(specification.getUnits());
			entity.setDescription(specification.getDescription());
			repository.save(entity);
		}
	}

	public Optional<Specification> getById(Integer id) {
		return repository.findById(id);
	}

	public void remove(Specification specification) throws SpecificationWithThisIdNotFound {
		if (repository.findById(specification.getId()).isEmpty()) {
			throw new SpecificationWithThisIdNotFound();
		} else {
			repository.delete(specification);
		}
	}
}
