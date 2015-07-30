package com.postal.model.user;

import com.postal.model.stamp.Stamp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brakhar on 31.05.15.
 */

@Entity
@Table(name = "user_stamp")
public class UserStamp implements Serializable{

    private static final long serialVersionUID = 2646303258445186771L;

    @EmbeddedId
    private UserStampPK id;

    @ManyToOne
    @JoinColumn(name = "user_name", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stamp_id", insertable = false, updatable = false)
    private Stamp stamp;

    @Column(name = "full_list_quantity")
    private int fullListQuantity;

    @Column(name = "one_line_quantity")
    private int oneLineQuantity;

    @Column(name = "one_piece_quantity")
    private int onePieceQuantity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stamp getStamp() {
        return stamp;
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
    }

    public UserStampPK getId() {
        return id;
    }

    public void setId(UserStampPK id) {
        this.id = id;
    }

    public int getFullListQuantity() {
        return fullListQuantity;
    }

    public void setFullListQuantity(int fullListQuantity) {
        this.fullListQuantity = fullListQuantity;
    }

    public int getOneLineQuantity() {
        return oneLineQuantity;
    }

    public void setOneLineQuantity(int oneLineQuantity) {
        this.oneLineQuantity = oneLineQuantity;
    }

    public int getOnePieceQuantity() {
        return onePieceQuantity;
    }

    public void setOnePieceQuantity(int onePieceQuantity) {
        this.onePieceQuantity = onePieceQuantity;
    }
}
