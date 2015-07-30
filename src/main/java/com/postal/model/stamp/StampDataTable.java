package com.postal.model.stamp;

import java.io.Serializable;

/**
 * Created by brakhar on 21.05.15.
 */
public class StampDataTable implements Serializable {

    private String name;
    private int year;
    private Long imgId;

    public StampDataTable() {
    }

    public StampDataTable(String name, int year, Long imgId) {
        this.name = name;
        this.year = year;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }
}
