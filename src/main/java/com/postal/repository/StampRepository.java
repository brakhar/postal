package com.postal.repository;

import com.postal.model.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by brakhar on 24.04.15.
 */

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Query("SELECT st FROM Stamp st WHERE st.catalogNumber like :searchValue OR year(st.publishDate) = :yearValue OR LOWER(st.title) like LOWER(:searchValue) ORDER BY st.catalogNumber")
    public Page<Stamp> findBySearchValue(@Param("searchValue") String searchValue, @Param("yearValue") Integer year, Pageable pageable);

    Integer countByOriginalStampId(Long stampId);

    Stamp getByCatalogNumber(String catalogNumber);

    @Query("SELECT stamp FROM Stamp stamp WHERE stamp.id not in (SELECT us.stamp.id FROM UserStamp us WHERE us.user.userName = :userName)")
    Page<Stamp> findByUserToBuyStamps(Pageable pageable, String userName);

}
