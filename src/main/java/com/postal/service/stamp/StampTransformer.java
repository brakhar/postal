package com.postal.service.stamp;

import com.postal.model.stamp.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by brakhar on 3/31/2015.
 */

@Component
public class StampTransformer {
    public List<Stamp> transform(List<HTMLStamp> htmlStampList) {
        List<Stamp> stampList = new ArrayList<Stamp>();
        for (HTMLStamp htmlStamp: htmlStampList){
            stampList.add(transformHtmlStamp(htmlStamp));
        }

        return stampList;
    }

    private Stamp transformHtmlStamp(HTMLStamp htmlStamp) {
        Stamp stamp = new Stamp();
/*
        stamp.setYear(htmlStamp.getYearPublish());
        stamp.setName(htmlStamp.getName());
*/
        stamp.setCatalogNumber(htmlStamp.getNumber());

        if(htmlStamp.getCategories() != null){
//            stamp.setCategories(transformHtmlCategories(htmlStamp.getCategories()));
        }

        if(htmlStamp.getImgId() != null && htmlStamp.getImgId() != 0){
            stamp.setStampImageId(htmlStamp.getImgId());
        }

        if (htmlStamp.getBigImgId() != null && htmlStamp.getBigImgId() != 0){
            stamp.setFullListImageId(htmlStamp.getBigImgId());
        }

        return stamp;
    }

    private Set<Category> transformHtmlCategories(List<String> htmlCategories){
        Set<Category> categoriesList = new HashSet<Category>();
        for(String htmlCategory: htmlCategories){
            categoriesList.add(new Category(htmlCategory));
        }

        return categoriesList;
    }

    public static Image transformHtmlImage(HTMLImage htmlImage){
        Image image = new Image();
        image.setWidth(htmlImage.getWidth());
        image.setHeight(htmlImage.getHeight());
        image.setAlt(htmlImage.getAlt());
        image.setContent(htmlImage.getContent());

        return image;
    }
}
