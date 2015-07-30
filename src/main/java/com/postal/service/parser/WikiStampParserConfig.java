package com.postal.service.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by brakhar on 20.07.15.
 */

@Configuration
@PropertySource("classpath:wiki-parser-config.properties")
public class WikiStampParserConfig {

    @Autowired
    private Environment env;

    private String xPathImageSrc;
    private String xPathYearList;
    private String mainPageURL;
    private String stampPageTemplate;
    private String xPathCountStampByPage1;
    private String xPathCountStampByPage2;
    private String xPathTitle;
    private String xPathDesign;
    private String xPathPublisDate;
    private String xPathCatalogNumber;
    private String xPathDenomination;
    private String xPathCirculation;
    private String xPathCountStampByPage3;

    @Bean
    public WikiStampParserConfig getWikiStampParserConfig() {

        WikiStampParserConfig wikiStampParserConfig = new WikiStampParserConfig();
        wikiStampParserConfig.setxPathImageSrc(env.getProperty("xpath.imageSrc"));
        wikiStampParserConfig.setxPathYearList(env.getProperty("xpath.year.list"));
        wikiStampParserConfig.setMainPageURL(env.getProperty("page.main"));
        wikiStampParserConfig.setStampPageTemplate(env.getProperty("page.year"));
        wikiStampParserConfig.setxPathCountStampByPage1(env.getProperty("xpath.stamp.table1.count"));
        wikiStampParserConfig.setxPathCountStampByPage2(env.getProperty("xpath.stamp.table2.count"));
        wikiStampParserConfig.setxPathCountStampByPage3(env.getProperty("xpath.stamp.table3.count"));
        wikiStampParserConfig.setxPathTitle(env.getProperty("xpath.titleSrc"));
        wikiStampParserConfig.setxPathDesign(env.getProperty("xpath.design"));
        wikiStampParserConfig.setxPathCirculation(env.getProperty("xpath.circulation"));
        wikiStampParserConfig.setxPathPublisDate(env.getProperty("xpath.datePublish"));
        wikiStampParserConfig.setxPathDenomination(env.getProperty("xpath.denomination"));
        wikiStampParserConfig.setxPathCatalogNumber(env.getProperty("xpath.catalogNumber"));
        return wikiStampParserConfig;
    }

    public void setxPathImageSrc(String xPathImageSrc) {
        this.xPathImageSrc = xPathImageSrc;
    }

    public String getxPathImageSrc() {
        return xPathImageSrc;
    }

    public String getxPathYearList() {
        return xPathYearList;
    }

    public void setxPathYearList(String xPathYearList) {
        this.xPathYearList = xPathYearList;
    }

    public String getMainPageURL() {
        return mainPageURL;
    }

    public void setMainPageURL(String mainPageURL) {
        this.mainPageURL = mainPageURL;
    }

    public String getStampPageTemplate() {
        return stampPageTemplate;
    }

    public void setStampPageTemplate(String stampPageTemplate) {
        this.stampPageTemplate = stampPageTemplate;
    }

    public String getxPathCountStampByPage1() {
        return xPathCountStampByPage1;
    }

    public void setxPathCountStampByPage1(String xPathCountStampByPage1) {
        this.xPathCountStampByPage1 = xPathCountStampByPage1;
    }

    public String getxPathCountStampByPage2() {
        return xPathCountStampByPage2;
    }

    public void setxPathCountStampByPage2(String xPathCountStampByPage2) {
        this.xPathCountStampByPage2 = xPathCountStampByPage2;
    }

    public String getxPathTitle() {
        return xPathTitle;
    }

    public void setxPathTitle(String xPathTitle) {
        this.xPathTitle = xPathTitle;
    }

    public String getxPathDesign() {
        return xPathDesign;
    }

    public void setxPathDesign(String xPathDesign) {
        this.xPathDesign = xPathDesign;
    }

    public String getxPathPublisDate() {
        return xPathPublisDate;
    }

    public void setxPathPublisDate(String xPathPublisDate) {
        this.xPathPublisDate = xPathPublisDate;
    }

    public String getxPathCatalogNumber() {
        return xPathCatalogNumber;
    }

    public void setxPathCatalogNumber(String xPathCatalogNumber) {
        this.xPathCatalogNumber = xPathCatalogNumber;
    }

    public String getxPathDenomination() {
        return xPathDenomination;
    }

    public void setxPathDenomination(String xPathDenomination) {
        this.xPathDenomination = xPathDenomination;
    }

    public String getxPathCirculation() {
        return xPathCirculation;
    }

    public void setxPathCirculation(String xPathCirculation) {
        this.xPathCirculation = xPathCirculation;
    }

    public String getxPathCountStampByPage3() {
        return xPathCountStampByPage3;
    }

    public void setxPathCountStampByPage3(String xPathCountStampByPage3) {
        this.xPathCountStampByPage3 = xPathCountStampByPage3;
    }
}