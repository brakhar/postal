package com.postal.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by brakhar on 01.06.15.
 */

@Embeddable
public class UserStampPK implements Serializable{
    private static final long serialVersionUID = -3950978277515114311L;

    @Column(name="user_name", insertable = false, updatable = false)
    private String userName;

    @Column(name="stamp_id", insertable = false, updatable = false)
    private Long stampId;

    public UserStampPK() {
    }

    public UserStampPK(String userName, Long stampId) {
        this.userName = userName;
        this.stampId = stampId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStampId() {
        return stampId;
    }

    public void setStampId(Long stampId) {
        this.stampId = stampId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStampPK that = (UserStampPK) o;

        if (!userName.equals(that.userName)) return false;
        return stampId.equals(that.stampId);

    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + stampId.hashCode();
        return result;
    }
}
