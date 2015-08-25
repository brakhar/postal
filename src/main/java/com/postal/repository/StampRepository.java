package com.postal.repository;

import com.postal.model.stamp.Stamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by brakhar on 24.04.15.
 */

public interface StampRepository extends JpaRepository<Stamp, Long> {

    @Query("SELECT st FROM Stamp st WHERE st.catalogNumber like :searchValue OR year(st.publishDate) = :yearValue OR LOWER(st.title) like LOWER(:searchValue) ORDER BY st.catalogNumber")
    public Page<Stamp> findBySearchValue(@Param("searchValue") String searchValue, @Param("yearValue") Integer year, Pageable pageable);

    Integer countByOriginalStampId(Long stampId);

    Stamp getByCatalogNumber(String catalogNumber);

    @Query("SELECT stamp FROM Stamp stamp WHERE stamp.id not in (SELECT us.stamp.id FROM UserStamp us WHERE us.user.userName = :userName) ORDER BY stamp.publishDate ASC")
    Page<Stamp> findStampsToBuyByUser(Pageable pageable, @Param("userName") String userName);

    @Query("SELECT stamp FROM Stamp stamp WHERE stamp.block = true AND stamp.id NOT IN (SELECT us.stamp.id FROM UserStamp us WHERE us.user.userName = :userName) ORDER BY stamp.publishDate ASC")
    Page<Stamp> findBlockToBuyByUser(Pageable pageable, @Param("userName") String userName);

    @Query("SELECT stamp FROM Stamp stamp WHERE stamp.smallPaper = true AND stamp.id NOT IN (SELECT us.stamp.id FROM UserStamp us WHERE us.user.userName = :userName) ORDER BY stamp.publishDate ASC")
    Page<Stamp> findSmallPaperToBuyByUser(Pageable pageable, @Param("userName") String userName);

    @Query("SELECT stamp FROM Stamp stamp ORDER BY stamp.publishDate DESC")
    Page<Stamp> getListOrderedByPublishDate(Pageable pageable);

    @Query("SELECT stamp FROM Stamp stamp WHERE year(stamp.publishDate) = :yearValue")
    Page<Stamp> getListByYear(Pageable pageable, @Param("yearValue") Integer year);

    @Query("SELECT distinct year(stamp.publishDate) FROM Stamp stamp ORDER BY year(stamp.publishDate) ASC")
    List<Integer> getYearList();


    @Query("SELECT stamp FROM Stamp stamp WHERE " +
            " (stamp.catalogNumber  = :catalogNumber OR '' = :catalogNumber) AND " +
            " (LOWER(stamp.title) like LOWER(:title) OR '' = :title) AND " +
            " (year(stamp.publishDate) = :yearValue OR 0 = :yearValue) ORDER BY stamp.catalogNumber")
    Page<Stamp> findStamps(Pageable pageable,
                           @Param("catalogNumber") String catalogNumber,
                           @Param("title") String title,
                           @Param("yearValue") Integer year);

    //@Query("SELECT count(*) FROM Stamp stamp WHERE stamp.block = true")
    Integer countByBlock(Boolean value);

    Integer countBySmallPaper(Boolean value);

    Integer countByStandard(Boolean value);

    @Query("SELECT count(*) FROM Stamp stamp WHERE stamp.block = false AND stamp.smallPaper = false AND stamp.standard = false")
    Integer getCountOther();
}
