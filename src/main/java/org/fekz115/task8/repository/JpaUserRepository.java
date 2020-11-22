package org.fekz115.task8.repository;

import org.fekz115.task8.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends UserRepository, CrudRepository<User, Integer> {}
