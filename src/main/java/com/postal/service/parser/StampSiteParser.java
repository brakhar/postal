package com.postal.service.parser;

import com.postal.exception.PostalRepositoryException;
import com.postal.model.stamp.Image;
import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.ImageGrabber;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brakhar on 26.06.15.
 */

@Component
public class StampSiteParser extends AbstractStampParser{

    private static Pattern GET_STAMP_ID_FROM_IMG_SRC_PATTERN =  Pattern.compile("(\\d{2,9})");
    private static Pattern GET_PUBLISH_DATE_FROM_PATTERN = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
    private static Pattern GET_CATALOG_NUMBER_FROM_PATTERN = Pattern.compile("(b|\\d{1,5})");
    private static Pattern GET_FULL_DESCRIPTION_OLD_HTML_FORMAT;

    private XPathExpression exprTitle;
    private XPathExpression exprPublishDateWithText;
    private XPathExpression exprCatalogNumberWithText;
    private XPathExpression exprFullDescriptionNew;
    private XPathExpression exprFullDescriptionOld;
    private XPathExpression exprPageNumbers;
    private XPathExpression exprStampImageSrcs;
    private XPathExpression exprBlockStampImageSrc;

    private StampSiteConfig stampSiteConfig;

    public StampSiteParser() {
        logger = LoggerFactory.getLogger(StampSiteParser.class);
    }

    @Autowired
    public StampSiteParser(StampSiteConfig stampSiteConfig){
        this.stampSiteConfig = stampSiteConfig;
    }


    @Autowired
    private ImageService imageService;

    @Autowired
    private StampService stampService;

    public boolean parseSite(boolean isFirstTime, int pageNumberRequest, long stampId){
        if (stampId != 0){
            String stampImgSrc = "shop_pict.php?aid=1&cid=" + stampId;
            saveStamp(stampImgSrc, isFirstTime);
            return true;
        }
        if (pageNumberRequest != 0){
            parseStampPage(pageNumberRequest, isFirstTime);
            return true;
        }
        List<Integer> pageNumbers = getPageNumbers();
        for(Integer pageNumber : pageNumbers){
            logger.debug("Number of page:" + pageNumber);
            parseStampPage(pageNumber, isFirstTime);
        }
        return true;
    }

    private void parseStampPage(Integer numberPage, boolean isFirstTime) {
        List<String> stampImgSrcs = getStampImageSrcs(numberPage);
        for (String imgSrc : stampImgSrcs){

            logger.debug("Stamp img src:" + imgSrc);
            saveStamp(imgSrc, isFirstTime);
        }
    }

    private void saveStamp(String imgSrc, boolean isFirstTime) {
        Long stampId = getStampIdByStampImageSrc(imgSrc);
        if(!isFirstTime){
            boolean isSavedAlready = stampService.checkIsStampIdPresentInDB(stampId);
            if(isSavedAlready){
                return;
            } else {
                System.out.println("New stamp was found!");
            }
        }

        Stamp stamp = getFullStampInfoWithoutBlockAndSmallImageSrc(stampId);

        setImages(stamp, imgSrc, stampId);

        try {
            stampService.insert(stamp);
        } catch (PostalRepositoryException e) {
            logger.error("Error during saving stamp with catalog number " + stamp.getCatalogNumber(), e);
        }
    }


    private void setImages(Stamp stamp, String stampImgSrc, Long stampId){
        byte[] stampImageContent = getImgContent(stampImgSrc);
        byte[] blockStampImageContent = getImgContent(getBlockStampImageSrc(stampId));
        Long stampImgId = imageService.insertImage(new Image(stampImageContent));
        Long blockStampImgId = imageService.insertImage(new Image(blockStampImageContent));
        stamp.setStampImageId(stampImgId);
        stamp.setFullListImageId(blockStampImgId);
    }

    private byte[] getImgContent(String imgSrc){
        return ImageGrabber.grab(stampSiteConfig.getDomain() + "/" + imgSrc);
    }



    public List<Integer> getPageNumbers() {
        List<Integer> pageNumbers = new ArrayList<>();

        try {
            NodeList nodes = (NodeList) exprPageNumbers.evaluate(getMainSiteDom(stampSiteConfig.getSiteURL()), XPathConstants.NODESET);

            pageNumbers.add(0, 1);

            for (int i = 1; i < nodes.getLength(); i++){
                pageNumbers.add(Integer.parseInt(nodes.item(i).getFirstChild().getNodeValue()));
            }
            return pageNumbers;
        } catch (XPathExpressionException e) {
            logger.error("Error getting pageNumbers by Xpath expression.", e);
        }

        return pageNumbers;
    }

    public List<String> getStampImageSrcs(int pageId) {
        NodeList nodes = null;

        try {
            nodes = (NodeList) exprStampImageSrcs.evaluate(getListStampPageDom(pageId), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            logger.error("Error getting all small stamp image src.", e);
        }

        List<String> imageSrcs = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++){
            imageSrcs.add(nodes.item(i).getFirstChild().getNodeValue());
        }
        return imageSrcs;
    }

    public String getBlockStampImageSrc(Long stampId) {
        String imgSrc = null;

        try {
            imgSrc = (String) exprBlockStampImageSrc.evaluate(getStampFullInfoDom(stampId), XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            logger.error("Error getting block of stamp images!", e);
        }

        return imgSrc;
    }

    public Stamp getFullStampInfoWithoutBlockAndSmallImageSrc(Long stampId) {

        Stamp stamp = new Stamp();
        Document stampFullInfoDom = getStampFullInfoDom(stampId);


        try {
            stamp.setOriginalStampId(stampId);
            stamp.setTitle((String) exprTitle.evaluate(stampFullInfoDom, XPathConstants.STRING));
            stamp.setPublishDate(getPublishDateFromText((String) exprPublishDateWithText.evaluate(stampFullInfoDom, XPathConstants.STRING)));
            setCatalogNumberFromText(stamp, stampFullInfoDom);
            setFullDescription(stamp, stampFullInfoDom);
        } catch (XPathExpressionException e) {
            logger.error("Error getting values of stamp from stamp page.", e);
        }

        return stamp;
    }

    private List<String> splitDescripionTextByLine(String fullDescription){
        List<String> descriptionLines = new ArrayList<>();
        descriptionLines.addAll(Arrays.asList(fullDescription.split("\r\n")));
        List<String> resultList = new ArrayList<>();

        for(String str: descriptionLines){
            if (str.trim().length() > 0) {
                str = str.replaceAll("\\u00A0", "");
                str = str.replaceAll("^ +| +$|( )+", " ");
                resultList.add(str);
            }
        }

        return resultList;
    }

    private List<String> getDescriptionList(Document stampFullInfoDom) throws XPathExpressionException {
        String fullDescriptionNew = (String) exprFullDescriptionNew.evaluate(stampFullInfoDom, XPathConstants.STRING);
        List<String> descriptionLines = splitDescripionTextByLine(fullDescriptionNew);
        if(descriptionLines.size() <= 1 ){
            descriptionLines.clear();
            String fullDescriptionOld = (String) exprFullDescriptionOld.evaluate(stampFullInfoDom, XPathConstants.STRING);
            Matcher fullDescriptionOldMatcher = GET_FULL_DESCRIPTION_OLD_HTML_FORMAT.matcher(fullDescriptionOld);
            while (fullDescriptionOldMatcher.find()){
                descriptionLines.add(fullDescriptionOldMatcher.group());
            }
        }
        return descriptionLines;
    }


    private void setFullDescription(Stamp stamp, Document stampFullInfoDom) throws XPathExpressionException {
        List<String> descriptionLines = getDescriptionList(stampFullInfoDom);
        for (String descriptionLine: descriptionLines){
            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsFormat())){
                stamp.setFormat(appendValue(stamp.getFormat(), descriptionLine, "\r\n"));
                continue;
            }

            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsDenominationStamp())){
                stamp.setDenomination(appendValue(stamp.getDenomination(), descriptionLine, "\r\n"));
                continue;
            }

            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsPerforation())){
                stamp.setPerforation(appendValue(stamp.getPerforation(), descriptionLine, "\r\n"));
                continue;
            }

            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsNumberStampInPiecePaper())){
                stamp.setNumberStampInPiecePaper(appendValue(stamp.getNumberStampInPiecePaper(), descriptionLine, "\r\n"));
                continue;
            }

            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsCirculation())){
                stamp.setCirculation(appendValue(stamp.getCirculation(), descriptionLine, "\r\n"));
                continue;
            }

            if(isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsDesign())){
                stamp.setDesign(appendValue(stamp.getDesign(), descriptionLine, "\r\n"));
                continue;
            }

            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsTypePublish())){
                stamp.setTypePublish(appendValue(stamp.getTypePublish(), descriptionLine, "\r\n"));
                continue;
            }

/*
            if (isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsSpecialNotes())){
                stamp.setSpecialNotes(appendValue(stamp.getSpecialNotes(), descriptionLine));
                continue;
            }
*/

            if(isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsSecurity())){
                stamp.setSecurity(appendValue(stamp.getSecurity(), descriptionLine, "\r\n"));
                continue;
            }

            if(isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsBarCode())){
                stamp.setBarCode(appendValue(stamp.getBarCode(), descriptionLine, "\r\n"));
                continue;
            }

            if(isContainsKeyWords(descriptionLine, stampSiteConfig.getParsingKeyWordsOriginOfPublish())){
                stamp.setOriginOfPublish(appendValue(stamp.getOriginOfPublish(), descriptionLine, "\r\n"));
                continue;
            } else {
                stamp.setSpecialNotes(appendValue(stamp.getSpecialNotes(), descriptionLine, "\r\n"));
            }

        }
    }

    private boolean isContainsKeyWords(String checkString, String keyWords){
        String[] keyWordsArray = keyWords.split("\\|");
        for (String str: keyWordsArray){
            if (checkString.startsWith(str)) return true;
        }
        return false;
    }

    private void setCatalogNumberFromText(Stamp stamp, Document stampFullInfoDom) {
        String catalogNumberWithText = null;
        boolean isBlock = false;
        try {
            catalogNumberWithText = (String) exprCatalogNumberWithText.evaluate(stampFullInfoDom, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            logger.error("Error getting catalog number from site.", e);
        }
        Matcher numberMatcher = GET_CATALOG_NUMBER_FROM_PATTERN.matcher(catalogNumberWithText);

        while(numberMatcher.find()){
            if("b".equals(numberMatcher.group())){
                isBlock = true;
                stamp.setBlock(isBlock);
                stamp.setCatalogNumber(catalogNumberWithText.replace(" ", ""));
            } else {
                if(isBlock) {
                    stamp.setBlockNumber(numberMatcher.group());
                } else {
                    stamp.setCatalogNumber(appendValue(stamp.getCatalogNumber(), numberMatcher.group(), " "));
                }
            }
        }
    }

    private Date getPublishDateFromText(String dateWithText) {
        Matcher dateMatcher = GET_PUBLISH_DATE_FROM_PATTERN.matcher(dateWithText);
        SimpleDateFormat parserSDF=new SimpleDateFormat("dd-MM-yyyy");
        Long stampId = null;
        if(dateMatcher.find()){
            try {
                return parserSDF.parse(dateMatcher.group());
            } catch (ParseException e) {
                logger.error("Error durign parsing Date", e);
            }
        }
        return null;
    }

    public Long getStampIdByStampImageSrc(String imgSrc) {
        Matcher imgSrcMatcher = GET_STAMP_ID_FROM_IMG_SRC_PATTERN.matcher(imgSrc);
        Long stampId = null;
        if(imgSrcMatcher.find()){
            stampId = Long.parseLong(imgSrcMatcher.group());
        }
        return stampId;
    }


    private Document getStampFullInfoDom(Long stampId){
        URL siteURL = null;
        try {
            siteURL = new URL(stampSiteConfig.getStampPageURL() + stampId);
            HtmlCleaner cleaner = new HtmlCleaner();
            cleaner.getProperties().setAdvancedXmlEscape(true);

            TagNode tagNode = cleaner.clean(siteURL);

            return new DomSerializer(
                    new CleanerProperties()).createDOM(tagNode);
        } catch (MalformedURLException e) {
            logger.error("Error getting DOM of full info stamp page!", e);
        } catch (ParserConfigurationException e) {
            logger.error("Error getting DOM of full info stamp page!", e);
        } catch (IOException e) {
            logger.error("Error getting DOM of full info stamp page!", e);
        }
        return null;
    }



    private Document getListStampPageDom(int pageId){
        URL siteURL = null;
        try {
            siteURL = new URL(stampSiteConfig.getStampListPageURL() + pageId);
            HtmlCleaner cleaner = new HtmlCleaner();
            cleaner.getProperties().setAdvancedXmlEscape(true);

            TagNode tagNode = cleaner.clean(siteURL);

            return new DomSerializer(
                    new CleanerProperties()).createDOM(tagNode);
        } catch (MalformedURLException e) {
            logger.error("Error getting DOM of main page!", e);
        } catch (ParserConfigurationException e) {
            logger.error("Error getting DOM of main page!", e);
        } catch (IOException e) {
            logger.error("Error getting DOM of main page!", e);
        }
        return null;
    }


    @PostConstruct
    public void initXpathExpressions(){
        stampSiteConfig = stampSiteConfig.getStampSiteConfig();
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            exprPageNumbers = xpath.compile(stampSiteConfig.getxPathPaginationAllPageNumbers());
            exprStampImageSrcs = xpath.compile(stampSiteConfig.getxPathAllSmallImageSrcByPage());
            exprBlockStampImageSrc = xpath.compile(stampSiteConfig.getxPathBlockImageSrc());

            exprTitle = xpath.compile(stampSiteConfig.getxPathStampTitle());
            exprPublishDateWithText = xpath.compile(stampSiteConfig.getxPathPublishDateWithText());
            exprCatalogNumberWithText = xpath.compile(stampSiteConfig.getxPathStampNumberWithText());
            exprFullDescriptionNew = xpath.compile(stampSiteConfig.getxPathStampFullDescriptionNew());
            exprFullDescriptionOld = xpath.compile(stampSiteConfig.getxPathStampFullDescriptionOld());

            GET_FULL_DESCRIPTION_OLD_HTML_FORMAT = Pattern.compile(stampSiteConfig.getRegexFullDescriptionOldHtml());

        } catch (XPathExpressionException e) {
            logger.error("Error during init xpath expression.", e);
        }
    }

    private String appendValue(String currentValue, String stringToAppend, String connector){
        if(connector == null) connector = "\r\n";
        if (currentValue != null && currentValue.trim().length() > 0) {
            return currentValue.trim() + connector + stringToAppend.trim();
        } else {
            return stringToAppend;
        }
    }

    public void setStampSiteConfig(StampSiteConfig stampSiteConfig) {
        this.stampSiteConfig = stampSiteConfig;
    }

}
