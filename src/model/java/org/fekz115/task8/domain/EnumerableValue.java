package org.fekz115.task8.domain;

import javax.persistence.*;

@Entity
public class EnumerableValue {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String value;

	@ManyToOne
	@JoinColumn(name = "enumerableSpecificationId")
	private EnumerableSpecification enumerableSpecification;

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
}
