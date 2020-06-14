package org.fekz115.task8.repository;

import org.fekz115.task8.domain.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByLoginAndPassword(String login, String password);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    Optional<User> findUserByLogin(String login);

    User save(User user);
}
