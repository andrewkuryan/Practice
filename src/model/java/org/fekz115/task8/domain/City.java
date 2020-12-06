package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	private boolean isDeliveryAvailable;
	private boolean isStoreAvailable;

	@OneToMany(
			mappedBy = "city",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<Store> stores = new HashSet<>();

	@OneToOne
	@JoinColumn(name = "coordsId")
	private Coords coords;

	@OneToMany(
			mappedBy = "deliveryArea",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<CityDeliveryArea> cityDeliveryAreas = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeliveryAvailable() {
		return isDeliveryAvailable;
	}

	public void setDeliveryAvailable(boolean deliveryAvailable) {
		isDeliveryAvailable = deliveryAvailable;
	}

	public boolean isStoreAvailable() {
		return isStoreAvailable;
	}

	public void setStoreAvailable(boolean storeAvailable) {
		isStoreAvailable = storeAvailable;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

	public Coords getCoords() {
		return coords;
	}

	public void setCoords(Coords coords) {
		this.coords = coords;
	}

	public Set<CityDeliveryArea> getCityDeliveryAreas() {
		return cityDeliveryAreas;
	}

	public void setCityDeliveryAreas(Set<CityDeliveryArea> cityDeliveryAreas) {
		this.cityDeliveryAreas = cityDeliveryAreas;
	}
}
