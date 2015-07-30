package com.postal.model.stamp;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nazar_Kharchuk on 3/31/2015.
 */

public class HTMLStamp {

    final static Logger logger = LoggerFactory.getLogger(HTMLStamp.class);

    private String number;
    private int yearPublish;
    private String name;
    private List<String> categories;
    private Long imgId;
    private Long bigImgId;

    public HTMLStamp() {}

    public HTMLStamp(Element imgElement) {

        String imageAltText = imgElement.attr("alt");
        String[] splitArray = imageAltText.split(" ");

        yearPublish =  Integer.parseInt(splitArray[0]);
        number = splitArray[1];
        name = imageAltText.substring(splitArray[0].length() + splitArray[1].length()+2, imageAltText.length());

        String themesTextNode = imgElement.parent().textNodes().get(2).text();

        categories = splitThemesString(themesTextNode);
    }


    public static boolean isSuitableStampForGrab(Element imgElement){
        String imageAltText = imgElement.attr("alt");
        String[] splitArray = imageAltText.split(" ");
        int yearPublish = 0;

        try {
            yearPublish =  Integer.parseInt(splitArray[0]);
        }catch (NumberFormatException ex){
            return false;
        }
        return true;

    }

    private List<String> splitThemesString(String themes){
        List<String> resultList = new ArrayList<String>();

        if (themes == null || themes.length() == 0) return resultList;


        //TODO: rewrite by using regexp
        themes = themes.substring(themes.indexOf(":")+1, themes.length());

        for (String theme: themes.split(",")){
            resultList.add(theme.trim());
        }

        return resultList;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getYearPublish() {
        return yearPublish;
    }

    public void setYearPublish(int yearPublish) {
        this.yearPublish = yearPublish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public Long getImgId() {
        return imgId;
    }

    public void setBigImgId(Long bigImgId) {
        this.bigImgId = bigImgId;
    }

    public Long getBigImgId() {
        return bigImgId;
    }
}
