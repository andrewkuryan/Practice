package org.fekz115.task8.service;

import org.fekz115.task8.domain.EnumerableSpecification;
import org.fekz115.task8.domain.EnumerableValue;
import org.fekz115.task8.repository.EnumerableSpecificationRepository;
import org.fekz115.task8.repository.EnumerableValueRepository;
import org.fekz115.task8.service.exception.specification.SpecificationWithTheSameNameAlreadyExistsException;
import org.fekz115.task8.service.exception.specification.SpecificationWithThisIdNotFound;

import java.util.List;
import java.util.Optional;

public class EnumerableSpecificationService {

	protected final EnumerableSpecificationRepository repository;
	private final EnumerableValueRepository valueRepository;

	public EnumerableSpecificationService(
			EnumerableSpecificationRepository repository,
			EnumerableValueRepository valueRepository
	) {
		this.repository = repository;
		this.valueRepository = valueRepository;
	}

	public Iterable<EnumerableSpecification> getSpecifications() {
		return repository.findAll();
	}

	public void save(
			EnumerableSpecification specification,
			List<EnumerableValue> newValues,
			List<Integer> oldValuesId
	) throws SpecificationWithTheSameNameAlreadyExistsException {
		if (repository.existsByNameAndIdNot(specification.getName(), specification.getId())) {
			throw new SpecificationWithTheSameNameAlreadyExistsException();
		} else {
			var entity = repository.findById(specification.getId()).orElse(specification);
			entity.setName(specification.getName());
			entity.setDescription(specification.getDescription());
			repository.save(entity);

			var entityValues = entity.getEnumerableValues();
			entityValues.forEach(value -> {
				if (!oldValuesId.contains(value.getId())) {
					valueRepository.delete(value);
				}
			});

			newValues.forEach(valueRepository::save);
		}
	}

	public Optional<EnumerableSpecification> getById(Integer id) {
		return repository.findById(id);
	}

	public void remove(EnumerableSpecification specification) throws SpecificationWithThisIdNotFound {
		if (repository.findById(specification.getId()).isEmpty()) {
			throw new SpecificationWithThisIdNotFound();
		} else {
			repository.delete(specification);
		}
	}
}
