package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPhotosRepository extends PhotosRepository, CrudRepository<Photo, Integer> {}
