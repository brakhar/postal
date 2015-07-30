package com.postal.service.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by brakhar on 26.06.15.
 */

@Configuration
@PropertySource("classpath:parser-config.properties")
public class StampSiteConfig {

    @Autowired
    private Environment env;

    private String siteURL;
    private String domain;
    private String stampListPageURL;
    private String stampPageURL;

    private String xPathPaginationAllPageNumbers;
    private String xPathAllSmallImageSrcByPage;
    private String xPathBlockImageSrc;
    private String xPathStampTitle;
    private String xPathPublishDateWithText;
    private String xPathStampNumberWithText;
    private String xPathStampFullDescriptionNew;
    private String xPathStampFullDescriptionOld;

    private String parsingKeyWordsFormat;
    private String parsingKeyWordsDenominationStamp;
    private String parsingKeyWordsPerforation;
    private String parsingKeyWordsOriginOfPublish;
    private String parsingKeyWordsNumberStampInPiecePaper;
    private String parsingKeyWordsCirculation;
    private String parsingKeyWordsDesign;
    private String parsingKeyWordsTypePublish;
    private String parsingKeyWordsSpecialNotes;
    private String parsingKeyWordsSecurity;
    private String parsingKeyWordsBarCode;
    private String regexFullDescriptionOldHtml;

    @Bean
    public StampSiteConfig getStampSiteConfig() {

        StampSiteConfig stampSiteConfig = new StampSiteConfig();

        stampSiteConfig.setSiteURL(env.getProperty("site.main.page.url"));
        stampSiteConfig.setDomain(env.getProperty("site.main.domain"));
        stampSiteConfig.setStampListPageURL(env.getProperty("site.main.stampList.url"));
        stampSiteConfig.setStampPageURL(env.getProperty("site.stamp.page.url"));

        stampSiteConfig.setxPathPaginationAllPageNumbers(env.getProperty("xpath.link.pagination.all.pageNumber"));
        stampSiteConfig.setxPathAllSmallImageSrcByPage(env.getProperty("xpath.link.all.image.small.src"));
        stampSiteConfig.setxPathBlockImageSrc(env.getProperty("xpath.link.image.block.src"));
        stampSiteConfig.setxPathStampTitle(env.getProperty("xpath.stamp.fullInfo.title"));
        stampSiteConfig.setxPathPublishDateWithText(env.getProperty("xpath.stamp.fullInfo.publishDateWithText"));
        stampSiteConfig.setxPathStampNumberWithText(env.getProperty("xpath.stamp.fullInfo.numberWithText"));
        stampSiteConfig.setxPathStampFullDescriptionNew(env.getProperty("xpath.stamp.fullInfo.fullDescription.new"));
        stampSiteConfig.setxPathStampFullDescriptionOld(env.getProperty("xpath.stamp.fullInfo.fullDescription.old"));

        stampSiteConfig.setParsingKeyWordsFormat(env.getProperty("parsing.keyWords.format"));
        stampSiteConfig.setParsingKeyWordsDenominationStamp(env.getProperty("parsing.keyWords.denominationStamp"));
        stampSiteConfig.setParsingKeyWordsPerforation(env.getProperty("parsing.keyWords.perforation"));
        stampSiteConfig.setParsingKeyWordsNumberStampInPiecePaper(env.getProperty("parsing.keyWords.numberStampInPiecePaper"));
        stampSiteConfig.setParsingKeyWordsCirculation(env.getProperty("parsing.keyWords.circulation"));
        stampSiteConfig.setParsingKeyWordsDesign(env.getProperty("parsing.keyWords.design"));
        stampSiteConfig.setParsingKeyWordsTypePublish(env.getProperty("parsing.keyWords.typePublish"));
        stampSiteConfig.setParsingKeyWordsSpecialNotes(env.getProperty("parsing.keyWords.specialNotes"));
        stampSiteConfig.setParsingKeyWordsOriginOfPublish(env.getProperty("parsing.keyWords.originOfPublish"));
        stampSiteConfig.setParsingKeyWordsSecurity(env.getProperty("parsing.keyWords.security"));
        stampSiteConfig.setParsingKeyWordsBarCode(env.getProperty("parsing.keyWords.barcode"));

        stampSiteConfig.setRegexFullDescriptionOldHtml(env.getProperty("regex.fullDescription"));

        return stampSiteConfig;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public String getSiteURL() {
        return siteURL;
    }

    public void setSiteURL(String siteURL) {
        this.siteURL = siteURL;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStampListPageURL() {
        return stampListPageURL;
    }

    public void setStampListPageURL(String stampListPageURL) {
        this.stampListPageURL = stampListPageURL;
    }

    public String getStampPageURL() {
        return stampPageURL;
    }

    public void setStampPageURL(String stampPageURL) {
        this.stampPageURL = stampPageURL;
    }

    public String getxPathPaginationAllPageNumbers() {
        return xPathPaginationAllPageNumbers;
    }

    public void setxPathPaginationAllPageNumbers(String xPathPaginationAllPageNumbers) {
        this.xPathPaginationAllPageNumbers = xPathPaginationAllPageNumbers;
    }

    public String getxPathAllSmallImageSrcByPage() {
        return xPathAllSmallImageSrcByPage;
    }

    public void setxPathAllSmallImageSrcByPage(String xPathAllSmallImageSrcByPage) {
        this.xPathAllSmallImageSrcByPage = xPathAllSmallImageSrcByPage;
    }

    public String getxPathBlockImageSrc() {
        return xPathBlockImageSrc;
    }

    public void setxPathBlockImageSrc(String xPathBlockImageSrc) {
        this.xPathBlockImageSrc = xPathBlockImageSrc;
    }

    public String getxPathStampTitle() {
        return xPathStampTitle;
    }

    public void setxPathStampTitle(String xPathStampTitle) {
        this.xPathStampTitle = xPathStampTitle;
    }

    public String getxPathPublishDateWithText() {
        return xPathPublishDateWithText;
    }

    public void setxPathPublishDateWithText(String xPathPublishDateWithText) {
        this.xPathPublishDateWithText = xPathPublishDateWithText;
    }

    public String getxPathStampNumberWithText() {
        return xPathStampNumberWithText;
    }

    public void setxPathStampNumberWithText(String xPathStampNumberWithText) {
        this.xPathStampNumberWithText = xPathStampNumberWithText;
    }

    public String getxPathStampFullDescriptionNew() {
        return xPathStampFullDescriptionNew;
    }

    public void setxPathStampFullDescriptionNew(String xPathStampFullDescriptionNew) {
        this.xPathStampFullDescriptionNew = xPathStampFullDescriptionNew;
    }

    public String getxPathStampFullDescriptionOld() {
        return xPathStampFullDescriptionOld;
    }

    public void setxPathStampFullDescriptionOld(String xPathStampFullDescriptionOld) {
        this.xPathStampFullDescriptionOld = xPathStampFullDescriptionOld;
    }

    public String getParsingKeyWordsFormat() {
        return parsingKeyWordsFormat;
    }

    public void setParsingKeyWordsFormat(String parsingKeyWordsFormat) {
        this.parsingKeyWordsFormat = parsingKeyWordsFormat;
    }

    public String getParsingKeyWordsDenominationStamp() {
        return parsingKeyWordsDenominationStamp;
    }

    public void setParsingKeyWordsDenominationStamp(String parsingKeyWordsDenominationStamp) {
        this.parsingKeyWordsDenominationStamp = parsingKeyWordsDenominationStamp;
    }

    public String getParsingKeyWordsPerforation() {
        return parsingKeyWordsPerforation;
    }

    public void setParsingKeyWordsPerforation(String parsingKeyWordsPerforation) {
        this.parsingKeyWordsPerforation = parsingKeyWordsPerforation;
    }

    public String getParsingKeyWordsOriginOfPublish() {
        return parsingKeyWordsOriginOfPublish;
    }

    public void setParsingKeyWordsOriginOfPublish(String parsingKeyWordsOriginOfPublish) {
        this.parsingKeyWordsOriginOfPublish = parsingKeyWordsOriginOfPublish;
    }

    public String getParsingKeyWordsNumberStampInPiecePaper() {
        return parsingKeyWordsNumberStampInPiecePaper;
    }

    public void setParsingKeyWordsNumberStampInPiecePaper(String parsingKeyWordsNumberStampInPiecePaper) {
        this.parsingKeyWordsNumberStampInPiecePaper = parsingKeyWordsNumberStampInPiecePaper;
    }

    public String getParsingKeyWordsCirculation() {
        return parsingKeyWordsCirculation;
    }

    public void setParsingKeyWordsCirculation(String parsingKeyWordsCirculation) {
        this.parsingKeyWordsCirculation = parsingKeyWordsCirculation;
    }

    public String getParsingKeyWordsDesign() {
        return parsingKeyWordsDesign;
    }

    public void setParsingKeyWordsDesign(String parsingKeyWordsDesign) {
        this.parsingKeyWordsDesign = parsingKeyWordsDesign;
    }

    public String getParsingKeyWordsTypePublish() {
        return parsingKeyWordsTypePublish;
    }

    public void setParsingKeyWordsTypePublish(String parsingKeyWordsTypePublish) {
        this.parsingKeyWordsTypePublish = parsingKeyWordsTypePublish;
    }

    public String getParsingKeyWordsSpecialNotes() {
        return parsingKeyWordsSpecialNotes;
    }

    public void setParsingKeyWordsSpecialNotes(String parsingKeyWordsSpecialNotes) {
        this.parsingKeyWordsSpecialNotes = parsingKeyWordsSpecialNotes;
    }

    public String getParsingKeyWordsSecurity() {
        return parsingKeyWordsSecurity;
    }

    public void setParsingKeyWordsSecurity(String parsingKeyWordsSecurity) {
        this.parsingKeyWordsSecurity = parsingKeyWordsSecurity;
    }

    public String getParsingKeyWordsBarCode() {
        return parsingKeyWordsBarCode;
    }

    public void setParsingKeyWordsBarCode(String parsingKeyWordsBarCode) {
        this.parsingKeyWordsBarCode = parsingKeyWordsBarCode;
    }

    public String getRegexFullDescriptionOldHtml() {
        return regexFullDescriptionOldHtml;
    }

    public void setRegexFullDescriptionOldHtml(String regexFullDescriptionOldHtml) {
        this.regexFullDescriptionOldHtml = regexFullDescriptionOldHtml;
    }
}
