package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProductEnumerableSpecification {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int enumerableSpecificationId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getEnumerableSpecificationId() {
			return enumerableSpecificationId;
		}

		public void setEnumerableSpecificationId(int enumerableSpecificationId) {
			this.enumerableSpecificationId = enumerableSpecificationId;
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "enumerableSpecificationId", updatable = false, insertable = false)
	private EnumerableSpecification enumerableSpecificationId;

	@ManyToOne
	@JoinColumn(name = "enumerableValueId", updatable = false, insertable = false)
	private EnumerableValue enumerableValue;

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public EnumerableSpecification getEnumerableSpecificationId() {
		return enumerableSpecificationId;
	}

	public void setEnumerableSpecificationId(EnumerableSpecification enumerableSpecificationId) {
		this.enumerableSpecificationId = enumerableSpecificationId;
	}

	public EnumerableValue getEnumerableValue() {
		return enumerableValue;
	}

	public void setEnumerableValue(EnumerableValue enumerableValue) {
		this.enumerableValue = enumerableValue;
	}
}
