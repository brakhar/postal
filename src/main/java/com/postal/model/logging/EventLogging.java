package com.postal.model.logging;

import com.postal.model.stamp.Stamp;
import com.postal.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by brakhar on 14.06.15.
 */

@Entity
@Table(name = "event_logging")
public class EventLogging implements Serializable {

    private static final long serialVersionUID = 5187059698843170631L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "stamp_id")
    private Stamp stamp;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    @Column(name = "action_date")
    private Date actionDate;

    @PrePersist
    protected void onCreate() {
        actionDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }
}

