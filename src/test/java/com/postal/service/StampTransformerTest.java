package com.postal.service;

import com.postal.model.stamp.HTMLImage;
import com.postal.model.stamp.HTMLStamp;
import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.StampTransformer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by brakhar on 02.04.15.
 */
public class StampTransformerTest {

    private static final String IMG_SRC = "/resources/img/src";
    private static final String NAME = "StampName";
    private static final String NUMBER = "StampNumber";
    private static final String ALT_STR = "AltStr";

    private static final Integer YEAR_PUBLISH = 1982;
    private static final int HEIGHT = 80;
    private static final int WIDTH = 90;
    public static final String CATEGORY_1 = "Theme1";
    public static final String CATEGORY_2 = "Theme2";
    public static final byte[] IMAGE_CONTENT = new byte[]{123, 123, 123};

    private StampTransformer underTest;
    private List<HTMLStamp> htmlStampList;

    private HTMLStamp htmlStamp1;

    private HTMLImage htmlImage;
    private List<String> categoriesList;

    @Before
    public void setUp(){
        underTest = new StampTransformer();
        htmlStampList = new ArrayList<HTMLStamp>();

        fillHtmlStampList();
        fillCategoryList();
    }

    private void fillCategoryList() {
        categoriesList = new ArrayList<String>();
        categoriesList.add(CATEGORY_1);
        categoriesList.add(CATEGORY_2);
    }

    private void fillHtmlStampList() {
        htmlStampList = new ArrayList<HTMLStamp>();
        htmlImage = new HTMLImage();
        htmlImage.setAlt(ALT_STR);
        htmlImage.setHeight(HEIGHT);
        htmlImage.setWidth(WIDTH);
        htmlImage.setSrc(IMG_SRC);
        htmlImage.setContent(IMAGE_CONTENT);

        htmlStamp1 = new HTMLStamp();

        htmlStamp1.setName(NAME);
        htmlStamp1.setCategories(categoriesList);
        htmlStamp1.setNumber(NUMBER);
        htmlStamp1.setYearPublish(YEAR_PUBLISH);

        htmlStampList.add(htmlStamp1);

    }

    @Test @Ignore
    public void shouldTransform(){
        fillHtmlStampList();
        List<Stamp> stampList = underTest.transform(htmlStampList);

        assertEquals(1, stampList.size());

        Stamp stamp1 = stampList.get(0);

/*
        assertEquals(YEAR_PUBLISH, stamp1.getYear());
        assertEquals(NAME, stamp1.getName());
*/
        assertEquals(NUMBER, stamp1.getCatalogNumber());

/*
        assertEquals(2, stamp1.getCategories().size());
*/
    }

    @Test @Ignore
    public void shouldTransformCategoriesNull(){
        fillHtmlStampList();
        htmlStamp1.setCategories(null);

        List<Stamp> stampList = underTest.transform(htmlStampList);

        assertEquals(1, stampList.size());

        Stamp stamp1 = stampList.get(0);

/*
        assertEquals(YEAR_PUBLISH, stamp1.getYear());
        assertEquals(NAME, stamp1.getName());
*/
        assertEquals(NUMBER, stamp1.getCatalogNumber());

/*
        assertEquals(0, stamp1.getCategories().size());
*/

    }



    @Test @Ignore
    public void shouldTransformImageNull(){
        fillHtmlStampList();
        List<Stamp> stampList = underTest.transform(htmlStampList);

        assertEquals(1, stampList.size());

        Stamp stamp1 = stampList.get(0);

/*
        assertEquals(YEAR_PUBLISH, stamp1.getYear());
        assertEquals(NAME, stamp1.getName());
*/
        assertEquals(NUMBER, stamp1.getCatalogNumber());

/*
        assertEquals(2, stamp1.getCategories().size());
*/
    }
}
