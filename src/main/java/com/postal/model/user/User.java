package com.postal.model.user;

import com.postal.model.logging.EventLogging;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brakhar on 01.06.15.
 */

@Entity
@Table(name = "users")
public class User implements Serializable{

    private static final long serialVersionUID = -3670447545117317639L;

    @Id
    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user")
    private Set<UserStamp> userStamps = new HashSet<UserStamp>();

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<UserRole>();

    @OneToMany(mappedBy = "user")
    private Set<EventLogging> eventLoggings = new HashSet<EventLogging>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public Set<UserStamp> getUserStamps() {
        return userStamps;
    }

    public void setUserStamps(Set<UserStamp> userStamps) {
        this.userStamps = userStamps;
    }

    public Set<EventLogging> getEventLoggings() {
        return eventLoggings;
    }

    public void setEventLoggings(Set<EventLogging> eventLoggings) {
        this.eventLoggings = eventLoggings;
    }
}
