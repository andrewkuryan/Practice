package org.fekz115.task8.service;

import org.fekz115.task8.domain.City;
import org.fekz115.task8.repository.CityRepository;
import org.fekz115.task8.service.exception.ServiceException;
import org.fekz115.task8.service.exception.city.CityWithTheSameNameExistsException;
import org.fekz115.task8.service.exception.city.CityWithThisIdNotExists;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class CityService {

    protected final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public void save(City city) throws ServiceException {
        if(repository.existsByNameAndIdNot(city.getName(), city.getId())) {
            throw new CityWithTheSameNameExistsException();
        } else {
            var entity = repository.findById(city.getId()).orElse(city);
            entity.setName(city.getName());
            repository.save(entity);
        }
    }

    public Iterable<City> getCities() {
        return repository.findAll();
    }

    public Optional<City> getCityById(Integer id) {
        return id == null ? Optional.empty() : repository.findById(id);
    }

    public void remove(City city) throws CityWithThisIdNotExists {
        if(repository.existsById(city.getId())){
            repository.delete(city);
        } else throw new CityWithThisIdNotExists();
    }
}
