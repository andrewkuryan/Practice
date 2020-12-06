package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CityDeliveryArea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "deliveryAreaId", updatable = false)
	private DeliveryArea deliveryArea;

	@ManyToOne
	@JoinColumn(name = "cityId", updatable = false)
	private City city;

	@OneToMany(
			mappedBy = "cityDeliveryArea",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<Order> orders = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DeliveryArea getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(DeliveryArea deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "CityDeliveryArea{" +
				"id=" + id +
				", deliveryAreaId=" + deliveryArea.getId() +
				", cityId=" + city.getId() +
				'}';
	}
}
