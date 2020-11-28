package org.fekz115.task8.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class ProductTag {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int tagId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getTagId() {
			return tagId;
		}

		public void setTagId(int tagId) {
			this.tagId = tagId;
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "tagId", updatable = false, insertable = false)
	private Tag tag;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}
}
