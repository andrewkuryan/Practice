package org.fekz115.task8.service;

import org.fekz115.task8.domain.Role;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.repository.UserRepository;
import org.fekz115.task8.service.exception.ServiceException;
import org.fekz115.task8.service.exception.userservice.LoginException;
import org.fekz115.task8.service.exception.userservice.UserWithTheSameEmailExistsException;
import org.fekz115.task8.service.exception.userservice.UserWithTheSameLoginExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

public class UserService{

    protected final UserRepository repository;
    protected final Function<String, String> encoder;

    public UserService(UserRepository repository, Function<String, String> encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public User login(User user) throws LoginException {
        encodeUserPassword(user);
        return repository.findByLoginAndPassword(user.getLogin(), user.getPassword())
                .orElseThrow(LoginException::new);
    }

    public void register(User user) throws ServiceException {
        encodeUserPassword(user);
        if(repository.existsByLogin(user.getLogin())) {
            throw new UserWithTheSameLoginExistsException();
        }
        if(repository.existsByEmail(user.getEmail())) {
            throw new UserWithTheSameEmailExistsException();
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        repository.save(user);
    }

    private void encodeUserPassword(User user) {
        user.setPassword(encoder.apply(user.getPassword()));
    }

    public Optional<User> getUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }
}