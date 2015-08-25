package com.postal.repository;

import com.postal.model.stamp.Stamp;
import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.model.user.UserStampPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Query("SELECT us FROM UserStamp us WHERE " +
            "  us.user.userName = :userName AND " +
            " (us.stamp.catalogNumber  = :catalogNumber OR '' = :catalogNumber) AND " +
            " (LOWER(us.stamp.title) like LOWER(:title) OR '' = :title) AND " +
            " (year(us.stamp.publishDate) = :yearValue OR 0 = :yearValue) ORDER BY us.stamp.catalogNumber")
    Page<UserStamp> findByFilter(
            @Param("userName") String userName,
            @Param("catalogNumber") String catalogNumber,
            @Param("title") String title,
            @Param("yearValue") Integer year,
            Pageable pageable
    );

    Integer countByUserUserNameAndStampBlock(String userName, Boolean isBlock);

    Integer countByUserUserNameAndStampSmallPaper(String userName, Boolean isSmallPaper);

    Integer countByUserUserNameAndStampStandard(String userName, Boolean isStandard);

    Integer countByUserUserNameAndStampBlockAndStampSmallPaperAndStampStandard(String userName, Boolean isBlock, Boolean isSmallPaper, Boolean isStandard);


    Integer countByUserUserNameAndStampBlockAndOnePieceQuantity(String userName, Boolean isBlock, Integer quantity);

    Integer countByUserUserNameAndStampSmallPaperAndOnePieceQuantity(String userName, Boolean isSmallPaper, Integer quantity);

    Integer countByUserUserNameAndStampStandardAndOnePieceQuantity(String userName, Boolean isStandard, Integer quantity);

    Integer countByUserUserNameAndStampBlockAndStampSmallPaperAndStampStandardAndOnePieceQuantity(String userName, Boolean isBlock, Boolean isSmallPaper, Boolean isStandard, Integer quantity);

    @Query("SELECT MAX(us.onePieceQuantity) FROM UserStamp us WHERE us.user.userName = :userName AND us.stamp.block = true")
    Integer maxByOnePieceAndUserUserNameAndBlock(String userName, Boolean isBlock);
}
