package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;
	private String description;
	private Double price;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(
			mappedBy = "cart",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<CartProduct> cartProducts = new HashSet<>();

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.REMOVE,
			orphanRemoval = true
	)
	private Set<ProductSpecification> specifications = new HashSet<>();

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.REMOVE,
			orphanRemoval = true
	)
	private Set<ProductEnumerableSpecification> enumerableSpecifications = new HashSet<>();

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<Photo> photos = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "producerId")
	private Producer producer;

	@OneToMany(
			mappedBy = "product",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.PERSIST,
			orphanRemoval = true
	)
	private Set<ProductTag> productTags = new HashSet<>();

	@OneToMany(
			mappedBy = "store",
			fetch = FetchType.EAGER, //TODO: make LAZY
			cascade = CascadeType.REMOVE,
			orphanRemoval = true
	)
	private Set<ProductStore> productStores = new HashSet<>();

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public Set<ProductSpecification> getSpecifications() {
		return specifications;
	}

	public void setSpecifications(Set<ProductSpecification> specifications) {
		this.specifications = specifications;
	}

	public Set<ProductEnumerableSpecification> getEnumerableSpecifications() {
		return enumerableSpecifications;
	}

	public void setEnumerableSpecifications(Set<ProductEnumerableSpecification> enumerableSpecifications) {
		this.enumerableSpecifications = enumerableSpecifications;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Set<ProductTag> getProductTags() {
		return productTags;
	}

	public void setProductTags(Set<ProductTag> productTags) {
		this.productTags = productTags;
	}

	public Set<ProductStore> getProductStores() {
		return productStores;
	}

	public void setProductStores(Set<ProductStore> productStores) {
		this.productStores = productStores;
	}
}
