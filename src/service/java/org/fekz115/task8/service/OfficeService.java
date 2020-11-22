package org.fekz115.task8.service;

import org.fekz115.task8.domain.Office;
import org.fekz115.task8.repository.OfficeRepository;
import org.fekz115.task8.service.exception.office.OfficeWithTheSameAddressExistsException;
import org.fekz115.task8.service.exception.office.OfficeWithThisIdNotFound;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class OfficeService {

    protected final OfficeRepository repository;

    public OfficeService(OfficeRepository repository) {
        this.repository = repository;
    }

    public void save(Office office) throws OfficeWithTheSameAddressExistsException {
        if(repository.existsByAddressAndCityAndIdNot(office.getAddress(), office.getCity(), office.getId())) {
            throw new OfficeWithTheSameAddressExistsException();
        } else {
            repository.save(office);
        }
    }

    public Optional<Office> getOfficeById(Integer id) {
        return repository.findById(id);
    }

    public void remove(Office office) throws OfficeWithThisIdNotFound {
        if(repository.existsById(office.getId())) {
            repository.delete(office);
        } else {
            throw new OfficeWithThisIdNotFound();
        }
    }

    public Iterable<Office> getAll() {
        return repository.findAll();
    }
}
