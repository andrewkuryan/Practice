package org.fekz115.task8.service;

import org.fekz115.task8.domain.City;
import org.fekz115.task8.domain.Coords;
import org.fekz115.task8.repository.CityRepository;
import org.fekz115.task8.repository.CoordsRepository;
import org.fekz115.task8.service.exception.ServiceException;
import org.fekz115.task8.service.exception.city.CityWithTheSameNameExistsException;
import org.fekz115.task8.service.exception.city.CityWithThisIdNotExists;

import java.util.Optional;

public class CityService {

    protected final CityRepository repository;
    protected final CoordsRepository coordsRepository;

    public CityService(CityRepository repository, CoordsRepository coordsRepository) {
        this.repository = repository;
        this.coordsRepository = coordsRepository;
    }

    public void save(City city, Coords coords) throws ServiceException {
        if(repository.existsByNameAndIdNot(city.getName(), city.getId())) {
            throw new CityWithTheSameNameExistsException();
        } else {
            var entity = repository.findById(city.getId()).orElse(city);
            entity.setName(city.getName());
            var cityCoords = entity.getCoords();
            if (cityCoords == null) {
                entity.setCoords(coords);
            } else {
                cityCoords.setLatitude(coords.getLatitude());
                cityCoords.setLongitude(coords.getLongitude());
            }
            coordsRepository.save(entity.getCoords());
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
