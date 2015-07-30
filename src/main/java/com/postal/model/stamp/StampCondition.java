package com.postal.model.stamp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brakhar on 23.03.15.
 */

@Entity
@Table(name = "stamp_condition")
public class StampCondition implements Serializable{

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    public StampCondition() {
    }

    public StampCondition(Long id) {
        this.id = id;
    }

    public StampCondition(String name) {
        this.name = name;
    }

    public StampCondition(Long id, String name) {
        this.id = id;
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
}
