package org.fekz115.task8.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany(
            mappedBy = "city",
            fetch = FetchType.EAGER, //TODO: make LAZY
            cascade=CascadeType.REMOVE,
            orphanRemoval=true
    )
    private Set<Office> offices;

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

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }
}
