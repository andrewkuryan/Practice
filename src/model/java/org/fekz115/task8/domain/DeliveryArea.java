package org.fekz115.task8.domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DeliveryArea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	private Time estimatedTime;

	@OneToOne
	@JoinColumn(name = "storeId")
	private Store store;

	@OneToMany(
			mappedBy = "city",
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

	public Time getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Time estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Set<CityDeliveryArea> getCityDeliveryAreas() {
		return cityDeliveryAreas;
	}

	public void setCityDeliveryAreas(Set<CityDeliveryArea> cityDeliveryAreas) {
		this.cityDeliveryAreas = cityDeliveryAreas;
	}
}
