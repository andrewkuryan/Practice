package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProductStore {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int storeId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getStoreId() {
			return storeId;
		}

		public void setStoreId(int storeId) {
			this.storeId = storeId;
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "storeId", updatable = false, insertable = false)
	private Store store;

	private int count;
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
