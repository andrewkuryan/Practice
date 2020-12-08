package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CartProduct {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int cartId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getCartId() {
			return cartId;
		}

		public void setCartId(int cartId) {
			this.cartId = cartId;
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "cartId", updatable = false, insertable = false)
	private Cart cart;

	private int count;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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

	@Override
	public String toString() {
		return "CartProduct{" +
				"productId=" + primaryKey.getProductId() +
				", cartId=" + primaryKey.getCartId() +
				", count=" + count +
				'}';
	}
}
