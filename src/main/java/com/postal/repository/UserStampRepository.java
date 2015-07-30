package com.postal.repository;

import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.model.user.UserStampPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Created by brakhar on 02.06.15.
 */
public interface UserStampRepository extends JpaRepository<UserStamp, UserStampPK> {

    Page<UserStamp> findByUser(Pageable pageable, User user);

    Page<UserStamp> findByUserOrderByStampIdAsc(Pageable pageable, User userName);

    @Query("SELECT us FROM UserStamp us WHERE us.user.userName = :userName AND us.stamp.id = :stampId")
    UserStamp findByUserNameStampId(@Param("userName") String userName, @Param("stampId") Long stampId);

    @Query("SELECT distinct us FROM UserStamp us WHERE ( year(us.stamp.publishDate) = :yearValue OR LOWER(us.stamp.title) like :searchValue) AND us.user.userName = :userName")
    Page<UserStamp> findBySearchValue(@Param("searchValue") String searchValue, @Param("userName") String userName, @Param("yearValue") Integer year, Pageable pageable);
}
