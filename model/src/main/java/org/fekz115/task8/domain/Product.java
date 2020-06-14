package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;
    private int cost;
    private String description;

    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.EAGER, //TODO: make LAZY
            cascade=CascadeType.REMOVE,
            orphanRemoval=true
    )
    private Set<Order> orders;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.EAGER, //TODO: make LAZY
            cascade=CascadeType.REMOVE,
            orphanRemoval=true
    )
    private Set<ProductSpecification> specifications;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.EAGER //TODO: make LAZY
    )
    private Set<Photo> photos;

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<ProductSpecification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Set<ProductSpecification> specifications) {
        this.specifications = specifications;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
