package com.postal.model.user;

import javax.persistence.*;

/**
 * Created by brakhar on 03.06.15.
 */

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_name")
    private User user;

    @Column(name = "role")
    private String role;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
