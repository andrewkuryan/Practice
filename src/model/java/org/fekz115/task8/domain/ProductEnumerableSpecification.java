package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class ProductEnumerableSpecification {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int enumerableSpecificationId;
		private int enumerableValueId;

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

		public int getEnumerableValueId() {
			return enumerableValueId;
		}

		public void setEnumerableValueId(int enumerableValueId) {
			this.enumerableValueId = enumerableValueId;
		}

		@Override
		public String toString() {
			return "ProductEnumerableSpecificationPk{" +
					"productId=" + productId +
					", enumerableSpecificationId=" + enumerableSpecificationId +
					", enumerableValueId=" + enumerableValueId +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			PrimaryKey that = (PrimaryKey) o;
			return productId == that.productId &&
					enumerableSpecificationId == that.enumerableSpecificationId &&
					enumerableValueId == that.enumerableValueId;
		}

		@Override
		public int hashCode() {
			return Objects.hash(productId, enumerableSpecificationId, enumerableValueId);
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "enumerableSpecificationId", updatable = false, insertable = false)
	private EnumerableSpecification enumerableSpecification;

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

	public EnumerableSpecification getEnumerableSpecification() {
		return enumerableSpecification;
	}

	public void setEnumerableSpecification(EnumerableSpecification enumerableSpecification) {
		this.enumerableSpecification = enumerableSpecification;
	}

	public EnumerableValue getEnumerableValue() {
		return enumerableValue;
	}

	public void setEnumerableValue(EnumerableValue enumerableValue) {
		this.enumerableValue = enumerableValue;
	}

	@Override
	public String toString() {
		return "ProductEnumerableSpecification{" +
				"productId=" + product.getId() +
				", enumerableSpecificationId=" + enumerableSpecification.getId() +
				", enumerableValueId=" + enumerableValue.getId() +
				'}';
	}
}
