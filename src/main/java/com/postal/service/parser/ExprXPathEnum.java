package com.postal.service.parser;

/**
 * Created by brakhar on 7/24/2015.
 */
public enum ExprXPathEnum {
    IMG_SRC_TABLE1(1),
    IMG_SRC_TABLE2(2),
    IMG_SRC_TABLE3(3),
    TITLE_TABLE1(1),
    TITLE_TABLE2(2),
    TITLE_TABLE3(3),
    DENOMINATION_TABLE1(1),
    DENOMINATION_TABLE2(2),
    DENOMINATION_TABLE3(3),
    CIRCULATION_TABLE1(1),
    CIRCULATION_TABLE2(2),
    CIRCULATION_TABLE3(3),
    PUBLISH_DATE_TABLE1(1),
    PUBLISH_DATE_TABLE2(2),
    PUBLISH_DATE_TABLE3(3),
    DESIGN_TABLE1(1),
    DESIGN_TABLE2(2),
    DESIGN_TABLE3(3),
    CATALOG_NUMBER_TABLE1(1),
    CATALOG_NUMBER_TABLE2(2),
    CATALOG_NUMBER_TABLE3(3);

    private Integer value;

    private ExprXPathEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}
