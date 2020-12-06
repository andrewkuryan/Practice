package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	private boolean isOrdered;
	private Double totalPrice;

	@OneToMany(
			mappedBy = "cart",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<CartProduct> cartProducts = new HashSet<>();

	@OneToOne(mappedBy = "cart")
	private Order order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean ordered) {
		isOrdered = ordered;
	}

	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
