package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String login;
	private String password;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "UserRole", joinColumns = @JoinColumn(name = "userId"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<Order> orders = new HashSet<>();

	private String email;

	private String activationCode;
	private boolean isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return String.format("User(login=%s, email=%s)", login, email);
	}
}
