package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String phone;
	private String address;

	@ManyToOne
	@JoinColumn(name = "cityId")
	private City city;

	@OneToOne
	@JoinColumn(name = "coordsId")
	private Coords coords;

	@OneToOne(mappedBy = "store")
	private DeliveryArea deliveryArea;

	@OneToMany(
			mappedBy = "store",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.REMOVE,
			orphanRemoval = true
	)
	private Set<ProductStore> productStores = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}

	public DeliveryArea getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(DeliveryArea deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public Set<ProductStore> getProductStores() {
		return productStores;
	}

	public void setProductStores(Set<ProductStore> productStores) {
		this.productStores = productStores;
	}

	@Override
	public String toString() {
		return "Store{" +
				"phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", cityId=" + city.getId() +
				", deliveryAreaId=" + (deliveryArea == null ? null : deliveryArea.getId()) +
				'}';
	}
}
