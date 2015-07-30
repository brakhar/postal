package com.postal.model.stamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brakhar on 01.03.15.
 */

@Entity
@Table(name = "category")
public class Category implements Serializable{

    private static final long serialVersionUID = 7530381823095226129L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "stamp_category",
            joinColumns = {@JoinColumn(name = "category_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "stamp_id", nullable = false, updatable = false)})

    private Set<Stamp> stamps = new HashSet<Stamp>();

    public Category() {}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(Set<Stamp> stamps) {
        this.stamps = stamps;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stamps=" + stamps +
                '}';
    }
}
