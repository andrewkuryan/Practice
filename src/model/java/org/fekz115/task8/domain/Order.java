package org.fekz115.task8.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "OrderTable")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	@JoinColumn(name = "cartId")
	private Cart cart;

	private Date createdAt;
	private String status;
	private String deliveryAddress;

	@ManyToOne
	@JoinColumn(name = "cityDeliveryAreaId")
	private CityDeliveryArea cityDeliveryArea;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public CityDeliveryArea getCityDeliveryArea() {
		return cityDeliveryArea;
	}

	public void setCityDeliveryArea(CityDeliveryArea cityDeliveryArea) {
		this.cityDeliveryArea = cityDeliveryArea;
	}
}
