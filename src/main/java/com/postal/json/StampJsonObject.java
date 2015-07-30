package com.postal.json;

import com.postal.model.stamp.Stamp;

import java.util.List;

/**
 * Created by brakhar on 19.05.15.
 */

public class StampJsonObject {

    long iTotalRecords;

    int iTotalDisplayRecords;

    String sEcho;

    String sColumns;

    List<Stamp> aaData;

    public long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public List<Stamp> getAaData() {
        return aaData;
    }

    public void setAaData(List<Stamp> aaData) {
        this.aaData = aaData;
    }
}
