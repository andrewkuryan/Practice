package org.fekz115.task8.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coords {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double latitude;
	private double longitude;

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

	@Override
	public String toString() {
		return "Coords{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
