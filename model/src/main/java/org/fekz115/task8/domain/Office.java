package org.fekz115.task8.domain;

import javax.persistence.*;

@Entity
public class Office {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String address;
    private String phone;

    @ManyToOne
    @JoinColumn(name="cityId", nullable=false)
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
