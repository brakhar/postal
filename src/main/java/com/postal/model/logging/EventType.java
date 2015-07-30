package com.postal.model.logging;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brakhar on 12.06.15.
 */

@Entity
@Table(name = "event_type")
public class EventType implements Serializable {

    private static final long serialVersionUID = 7379612566790701731L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "eventType")
    private Set<EventLogging> eventLoggings = new HashSet<EventLogging>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EventLogging> getEventLoggings() {
        return eventLoggings;
    }

    public void setEventLoggings(Set<EventLogging> eventLoggings) {
        this.eventLoggings = eventLoggings;
    }
}
