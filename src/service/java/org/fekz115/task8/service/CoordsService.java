package org.fekz115.task8.service;

import org.fekz115.task8.domain.Coords;
import org.fekz115.task8.repository.CoordsRepository;

public class CoordsService {

	protected final CoordsRepository coordsRepository;

	public CoordsService(CoordsRepository coordsRepository) {
		this.coordsRepository = coordsRepository;
	}

	public void save(Coords coords) {
		coordsRepository.save(coords);
	}
}
