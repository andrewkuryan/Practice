package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class EnumerableValue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String value;

	@ManyToOne
	@JoinColumn(name = "enumerableSpecificationId")
	private EnumerableSpecification enumerableSpecification;

	@OneToMany(
			mappedBy = "ProductEnumerableSpecification",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<ProductEnumerableSpecification> productEnumerableSpecifications = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EnumerableSpecification getEnumerableSpecification() {
		return enumerableSpecification;
	}

	public void setEnumerableSpecification(EnumerableSpecification enumerableSpecification) {
		this.enumerableSpecification = enumerableSpecification;
	}

	public Set<ProductEnumerableSpecification> getProductEnumerableSpecifications() {
		return productEnumerableSpecifications;
	}

	public void setProductEnumerableSpecifications(Set<ProductEnumerableSpecification> productEnumerableSpecifications) {
		this.productEnumerableSpecifications = productEnumerableSpecifications;
	}
}
