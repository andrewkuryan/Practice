package org.fekz115.task8.repository;

import org.fekz115.task8.domain.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOfficeRepository extends OfficeRepository, CrudRepository<Office, Integer> {}
