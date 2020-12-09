package org.fekz115.task8.service;

import org.fekz115.task8.domain.Role;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.repository.UserRepository;
import org.fekz115.task8.service.exception.ServiceException;
import org.fekz115.task8.service.exception.userservice.LoginException;
import org.fekz115.task8.service.exception.userservice.UserWithTheSameEmailExistsException;
import org.fekz115.task8.service.exception.userservice.UserWithTheSameLoginExistsException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class UserService {

	protected final UserRepository repository;
	protected final Function<String, String> encoder;

	public UserService(UserRepository repository, Function<String, String> encoder) {
		this.repository = repository;
		this.encoder = encoder;
		init();
	}

	private void init() {
		Iterable<User> users = repository.findAll();
		StreamSupport.stream(users.spliterator(), false)
				.filter(user -> user.getRoles().contains(Role.ADMIN))
				.findAny()
				.ifPresentOrElse((user) -> {}, () -> {
					StreamSupport.stream(users.spliterator(), false)
							.filter(user -> user.getLogin().equals("admin"))
							.findFirst()
							.ifPresentOrElse(user -> {
								user.getRoles().add(Role.ADMIN);
								repository.save(user);
							}, () -> {
								User admin = new User();
								admin.setLogin("admin");
								admin.setPassword("admin");
								admin.setRoles(Set.of(Role.ADMIN, Role.USER));
								admin.setActive(true);
								encodeUserPassword(admin);
								repository.save(admin);
							});
				});
	}

	public User login(User user) throws LoginException {
		encodeUserPassword(user);
		return repository.findByLoginAndPassword(user.getLogin(), user.getPassword())
				.orElseThrow(LoginException::new);
	}

	public void register(User user) throws ServiceException {
		encodeUserPassword(user);
		if (repository.existsByLogin(user.getLogin())) {
			throw new UserWithTheSameLoginExistsException();
		}
		if (user.getEmail() != null && repository.existsByEmail(user.getEmail())) {
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

	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}

	public void remove(Integer id) {
		repository.deleteById(id);
	}

	public Optional<User> getUserById(Integer id) {
		return repository.findById(id);
	}

	public void save(User user) {
		repository.save(user);
	}
}
