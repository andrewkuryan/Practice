package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class OrderProductStore {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int orderId;
		private int storeId;
		private int productId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getOrderId() {
			return orderId;
		}

		public void setOrderId(int orderId) {
			this.orderId = orderId;
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

	private int count;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "orderId", updatable = false, insertable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "storeId", updatable = false, insertable = false)
	private Store store;

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
