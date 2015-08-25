package com.postal.model.stamp;

import com.postal.model.logging.EventLogging;
import com.postal.model.user.UserStamp;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brakhar on 01.03.15.
 */

@Entity
@Table(name = "stamp")
@Proxy(lazy=false)
public class Stamp implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Stamp.class);

    private static final long serialVersionUID = 5385448736596738252L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "{addStamp.error.catalogNumber}")
    @Column(name = "catalog_number")
    private String catalogNumber;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull(message="{addStamp.error.publishDateRequired}")
    @Column(name = "publish_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date publishDate;

    @Column(name = "format")
    private String format;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "perforation")
    private String perforation;

    @Column(name = "origin_Publish")
    private String originOfPublish;

    @Column(name = "number_stamp_in_piece_paper")
    private String numberStampInPiecePaper;

    @Column(name = "circulation")
    private String circulation;

    @Column(name = "design")
    private String design;

    @Column(name = "type_publish")
    private String typePublish;

    @Column(name = "special_notes")
    private String specialNotes;

    @Column(name = "stamp_image_id")
    private Long stampImageId;

    @Column(name = "full_list_image_id")
    private Long fullListImageId;

    @Column(name = "security")
    private String security;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "original_stamp_id")
    private Long originalStampId;

    @Column(name = "block")
    private boolean block;

    @Column(name = "block_number")
    private String blockNumber;

    @Column(name = "small_paper")
    private boolean smallPaper;

    @Column(name = "small_paper_number")
    private String smallPaperNumber;

    @Column(name = "standard")
    private boolean standard;

    @Column(name = "standard_number")
    private String standardNumber;

/*
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "stamp_category",
            joinColumns = {@JoinColumn(name = "stamp_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "category_id", nullable = false, updatable = false)})
    private Set<Category> categories = new HashSet<Category>();
*/

    @OneToMany(mappedBy = "stamp")
    private Set<UserStamp> userStamps = new HashSet<UserStamp>();

    @OneToMany(mappedBy = "stamp")
    private Set<EventLogging> eventLoggings = new HashSet<EventLogging>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getPerforation() {
        return perforation;
    }

    public void setPerforation(String perforation) {
        this.perforation = perforation;
    }

    public String getOriginOfPublish() {
        return originOfPublish;
    }

    public void setOriginOfPublish(String originOfPublish) {
        this.originOfPublish = originOfPublish;
    }

    public String getNumberStampInPiecePaper() {
        return numberStampInPiecePaper;
    }

    public void setNumberStampInPiecePaper(String numberStampInPiecePaper) {
        this.numberStampInPiecePaper = numberStampInPiecePaper;
    }

    public String getCirculation() {
        return circulation;
    }

    public void setCirculation(String circulation) {
        this.circulation = circulation;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getTypePublish() {
        return typePublish;
    }

    public void setTypePublish(String typePublish) {
        this.typePublish = typePublish;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public Long getStampImageId() {
        return stampImageId;
    }

    public void setStampImageId(Long stampImageId) {
        this.stampImageId = stampImageId;
    }

    public Long getFullListImageId() {
        return fullListImageId;
    }

    public void setFullListImageId(Long fullListImageId) {
        this.fullListImageId = fullListImageId;
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

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getSecurity() {
        return security;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setOriginalStampId(Long originalStampId) {
        this.originalStampId = originalStampId;
    }

    public Long getOriginalStampId() {
        return originalStampId;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getSmallPaperNumber() {

        return smallPaperNumber;
    }

    public void setSmallPaperNumber(String smallPaperNumber) {
        this.smallPaperNumber = smallPaperNumber;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isSmallPaper() {
        return smallPaper;
    }

    public void setSmallPaper(boolean smallPaper) {
        this.smallPaper = smallPaper;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public String getStandardNumber() {
        return standardNumber;
    }

    public void setStandardNumber(String standardNumber) {
        this.standardNumber = standardNumber;
    }
}
