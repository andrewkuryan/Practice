package org.fekz115.task8.domain;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String path;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
