package org.fekz115.task8.domain;

import javax.persistence.*;

@Entity
public class Coords {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double latitude;
	private double longitude;

	@OneToOne(mappedBy = "coords")
	private Store store;

	@OneToOne(mappedBy = "coords")
	private City city;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
