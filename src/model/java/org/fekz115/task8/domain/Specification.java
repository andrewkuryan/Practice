package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Specification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	private String units;
	private String description;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<ProductSpecification> specifications = new HashSet<>();

	public Set<ProductSpecification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<ProductSpecification> specifications) {
		this.specifications = specifications;
	}

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

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
