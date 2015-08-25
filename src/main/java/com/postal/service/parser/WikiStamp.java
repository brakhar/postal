package com.postal.service.parser;

import java.util.Date;

/**
 * Created by brakhar on 23.07.15.
 */
public class WikiStamp {

    private byte[] image;
    private String design;
    private Date publishingDate;
    private String circulation;
    private String catalogNumber;
    private String title;
    private String denomination;

    public WikiStamp(byte[] image, String design, Date publishingDate, String circulation, String catalogNumber, String title, String denomination) {
        this.image = image;
        this.design = design;
        this.publishingDate = publishingDate;
        this.circulation = circulation;
        this.catalogNumber = catalogNumber;
        this.title = title;
        this.denomination = denomination;
    }

    public WikiStamp() {}

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getCirculation() {
        return circulation;
    }

    public void setCirculation(String circulation) {
        this.circulation = circulation;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }
}
