package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class EnumerableSpecification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String description;
	private String name;

	@OneToMany(
			mappedBy = "EnumerableSpecification",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<EnumerableValue> enumerableValues = new HashSet<>();

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<ProductEnumerableSpecification> enumerableSpecifications = new HashSet<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<EnumerableValue> getEnumerableValues() {
		return enumerableValues;
	}

	public void setEnumerableValues(Set<EnumerableValue> enumerableValues) {
		this.enumerableValues = enumerableValues;
	}

	public Set<ProductEnumerableSpecification> getEnumerableSpecifications() {
		return enumerableSpecifications;
	}

	public void setEnumerableSpecifications(Set<ProductEnumerableSpecification> enumerableSpecifications) {
		this.enumerableSpecifications = enumerableSpecifications;
	}
}
