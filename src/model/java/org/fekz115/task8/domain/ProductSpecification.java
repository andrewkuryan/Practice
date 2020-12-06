package org.fekz115.task8.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProductSpecification implements Serializable {

	@Embeddable
	public static class PrimaryKey implements Serializable {

		private int productId;
		private int specificationId;

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

		public int getSpecificationId() {
			return specificationId;
		}

		public void setSpecificationId(int specificationId) {
			this.specificationId = specificationId;
		}
	}

	@EmbeddedId
	private PrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "productId", updatable = false, insertable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "specificationId", updatable = false, insertable = false)
	private Specification specification;

	private String value;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Specification getSpecification() {
		return specification;
	}

	public void setSpecification(Specification specification) {
		this.specification = specification;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString() {
		return "ProductSpecification{" +
				"productId=" + product.getId() +
				", specificationId=" + specification.getId() +
				", value='" + value + '\'' +
				'}';
	}
}
