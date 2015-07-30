package com.postal.service.parser;

import com.postal.exception.PostalRepositoryException;
import com.postal.model.stamp.Image;
import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.ImageGrabber;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.xpath.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brakhar on 22.07.15.
 */

@Component("wikiStampParserService")
public class WikiStampParserService extends AbstractStampParser{

    @Autowired
    private WikiStampParserConfig wikiStampParserConfig;

    @Autowired
    private ImageGrabber imageGrabber;

    @Autowired
    private ImageService imageService;

    @Autowired
    private StampService stampService;

    private XPath xpath;

    private Integer numberPages;
    private XPathExpression exprYearList;

    private Map<ExprXPathEnum, List<XPathExpression>> exprXpathMap = new HashMap<ExprXPathEnum, List<XPathExpression>>();

    private XPathExpression exprTable1CountStamp;
    private XPathExpression exprTable2CountStamp;
    private XPathExpression exprTable3CountStamp;

    private static Pattern GET_HTTP_IMG_URL =  Pattern.compile("[^//].*");
    private static Pattern GET_STAMP_NUMBER =  Pattern.compile("[-,0-9]+");


    public WikiStampParserService() {
        logger = LoggerFactory.getLogger(WikiStampParserService.class);
        initExpressionMap();
    }


    public void updateStampsByWikiStampInfo(){
        for (int year = 1992; year < Calendar.getInstance().get(Calendar.YEAR); year ++){
            updateStampByYear(year);
        }
    }

    public void updateStampByYear(int year) {
        List<WikiStamp> wikiStamps;
        wikiStamps = getStampInfoList(year, true);

        for (WikiStamp wikiStamp : wikiStamps){

            String catalogNumber = wikiStamp.getCatalogNumber();

            if (catalogNumber != null && catalogNumber.length() > 0){
                updateStamp(wikiStamp);
            }
        }
    }

    private void updateStamp(WikiStamp wikiStamp) {
        Stamp stamp = null;
        try{
            stamp = stampService.getByCatalogNumber(wikiStamp.getCatalogNumber());
        } catch (Exception ex){
            logger.error("Error during getting stamp. Update stamp. Catalog Number -" + wikiStamp.getCatalogNumber() + " ", ex);
        }
        if(stamp != null && (stamp.getStampImageId() == null || stamp.getStampImageId() == 0)){
            stamp.setStampImageId(imageService.insertImage(new Image(wikiStamp.getImage())));

            if(stamp.getCirculation() == null || stamp.getCirculation().trim().length() == 0){
                stamp.setCirculation(wikiStamp.getCirculation());
            }

            if(stamp.getDenomination() == null || stamp.getDenomination().trim().length() == 0){
                stamp.setDenomination(wikiStamp.getDenomination());
            }

            if(stamp.getDesign() == null || stamp.getDesign().trim().length() == 0) {
                stamp.setDesign(wikiStamp.getDesign());
            }

            try {
                stampService.update(stamp);
                System.out.println("Stamp updated. Stamp catalog number: " + stamp.getCatalogNumber());
            } catch (PostalRepositoryException e) {
                logger.error("Error during updating stamp with Wiki Data");
            }
        }
    }


    public List<Integer> getYears(){
        List<Integer> years = new ArrayList<>();
        try {
            Document doc = getMainSiteDom(wikiStampParserConfig.getStampPageTemplate() + 1992);

            String[] yearsNodeList = exprYearList.evaluate(doc, XPathConstants.STRING).toString().split("\n");
            for (int i = 1; i < yearsNodeList.length; i++){

                years.add(Integer.parseInt(yearsNodeList[i]));
            }
        } catch (XPathExpressionException e) {
            logger.error("Error getting list of years.", e);
        }
        return years;
    }


    @PostConstruct
    public void initXpathExpressions() {
        xpath = XPathFactory.newInstance().newXPath();
        wikiStampParserConfig = wikiStampParserConfig.getWikiStampParserConfig();
        try {
            exprYearList = xpath.compile(wikiStampParserConfig.getxPathYearList());
            exprTable1CountStamp = xpath.compile(wikiStampParserConfig.getxPathCountStampByPage1());
            exprTable2CountStamp = xpath.compile(wikiStampParserConfig.getxPathCountStampByPage2());
            exprTable3CountStamp = xpath.compile(wikiStampParserConfig.getxPathCountStampByPage3());
        }catch (XPathExpressionException e) {
            logger.error("Error during init XpatExpression. ", e);
        }
    }

    public int getFullNumberStampByYear(int year) {
        Document doc = getMainSiteDom(wikiStampParserConfig.getStampPageTemplate() + year);
        return getNumberStampFirstTable(doc) + getNumberStampSecondTable(doc) + getNumberStampThirdTable(doc);
    }

    private int getNumberStampFirstTable(Document page){
        double count1 = 0d;
        try {
            count1 = (Double) exprTable1CountStamp.evaluate(page, XPathConstants.NUMBER);

        } catch (XPathExpressionException e) {
            logger.error("Error during compile exprStampImage.", e);
        }
        return (int)count1;
    }

    private int getNumberStampSecondTable(Document page){
        double count2 = 0d;
        try {
            count2 = (Double) exprTable2CountStamp.evaluate(page, XPathConstants.NUMBER);

        } catch (XPathExpressionException e) {
            logger.error("Error during compile exprStampImage.", e);
        }
        return (int)count2;
    }


    private int getNumberStampThirdTable(Document page){
        double count3 = 0d;
        try {
            count3 = (Double) exprTable3CountStamp.evaluate(page, XPathConstants.NUMBER);

        } catch (XPathExpressionException e) {
            logger.error("Error during compile exprStampImage.", e);
        }
        return count3 == 1d ? 0 : (int)count3;
    }


    private boolean isNeedForUpdate(String catalog_number){
        Stamp stamp = null;
        try{
            stamp = stampService.getByCatalogNumber(catalog_number);
        } catch (Exception ex){
            logger.error("Error during getting stamp. isNeedForUpdate. Catalog Number -" + catalog_number + " ", ex);
        }
        if(stamp == null) return  false;

        if(stamp.getStampImageId() == null || stamp.getStampImageId() == 0)
            return true;

        if(stamp.getCirculation() == null || stamp.getCirculation().trim().length() == 0)
            return true;

        if(stamp.getDenomination() == null || stamp.getDenomination().trim().length() == 0)
            return true;

        if(stamp.getDesign() == null || stamp.getDesign().trim().length() == 0)
            return true;

        return false;
    }


    public List<WikiStamp> getStampInfoList(int year, boolean getOnlyForUpdate) {
        List<WikiStamp> wikiStamps = new ArrayList<WikiStamp>();

        Document page = getMainSiteDom(wikiStampParserConfig.getStampPageTemplate() + year);

        int numberStampFirstTable = getNumberStampFirstTable(page);
        int numberStampSecondTable = getNumberStampSecondTable(page);
        int numberStampThirdTable = getNumberStampThirdTable(page);

        String imageSrc = null;
        String author = null;
        String publishDate = null;
        String circulation = null;
        String denomination = null;
        String catalogNumber = null;
        String title = null;
        WikiStamp wikiStamp = null;


        try {

            initExprPathWikiStamp(numberStampFirstTable, numberStampSecondTable, numberStampThirdTable);

            //first table
            for (int i = 2; i <= numberStampFirstTable; i++){
                catalogNumber = (String)getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE1, i).evaluate(page, XPathConstants.STRING);
                catalogNumber = getStampCatalogNumber(catalogNumber);

                if((catalogNumber != null && catalogNumber.trim().length() > 0) && (isNeedForUpdate(catalogNumber) || !getOnlyForUpdate)){
                    imageSrc = (String)getExprXpath(ExprXPathEnum.IMG_SRC_TABLE1, i).evaluate(page, XPathConstants.STRING);
                    author = (String)getExprXpath(ExprXPathEnum.AUTHOR_TABLE1, i).evaluate(page, XPathConstants.STRING);
                    circulation = (String)getExprXpath(ExprXPathEnum.CIRCULATION_TABLE1, i).evaluate(page, XPathConstants.STRING);
                    denomination = (String)getExprXpath(ExprXPathEnum.DENOMINATION_TABLE1, i).evaluate(page, XPathConstants.STRING);
                    title  = (String)getExprXpath(ExprXPathEnum.TITLE_TABLE1, i).evaluate(page, XPathConstants.STRING);

                    wikiStamps.add(new WikiStamp(getWikiImageContent(imageSrc), author, null, circulation, catalogNumber, title, denomination));
                }
            }

            //second table
            for (int i = 2; i <= numberStampSecondTable; i++){
                catalogNumber = (String)getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE2, i).evaluate(page, XPathConstants.STRING);
                catalogNumber = getStampCatalogNumber(catalogNumber);

                if((catalogNumber != null && catalogNumber.trim().length() > 0) && (isNeedForUpdate(catalogNumber) || !getOnlyForUpdate)){

                    imageSrc = (String) getExprXpath(ExprXPathEnum.IMG_SRC_TABLE2, i).evaluate(page, XPathConstants.STRING);
                    author = (String) getExprXpath(ExprXPathEnum.AUTHOR_TABLE2, i).evaluate(page, XPathConstants.STRING);
                    circulation = (String) getExprXpath(ExprXPathEnum.CIRCULATION_TABLE2, i).evaluate(page, XPathConstants.STRING);
                    denomination = (String) getExprXpath(ExprXPathEnum.DENOMINATION_TABLE2, i).evaluate(page, XPathConstants.STRING);
                    title = (String) getExprXpath(ExprXPathEnum.TITLE_TABLE2, i).evaluate(page, XPathConstants.STRING);

                    wikiStamps.add(new WikiStamp(getWikiImageContent(imageSrc), author, null, circulation, catalogNumber, title, denomination));
                }
            }

            //Third table
            for (int i = 2; i <= numberStampThirdTable; i++){
                catalogNumber = (String)getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE3, i).evaluate(page, XPathConstants.STRING);
                catalogNumber = getStampCatalogNumber(catalogNumber);

                if((catalogNumber != null && catalogNumber.trim().length() > 0) && (isNeedForUpdate(catalogNumber) || !getOnlyForUpdate)){
                    imageSrc = (String) getExprXpath(ExprXPathEnum.IMG_SRC_TABLE3, i).evaluate(page, XPathConstants.STRING);
                    author = (String) getExprXpath(ExprXPathEnum.AUTHOR_TABLE3, i).evaluate(page, XPathConstants.STRING);
                    circulation = (String) getExprXpath(ExprXPathEnum.CIRCULATION_TABLE3, i).evaluate(page, XPathConstants.STRING);
                    denomination = (String) getExprXpath(ExprXPathEnum.DENOMINATION_TABLE3, i).evaluate(page, XPathConstants.STRING);
                    title = (String) getExprXpath(ExprXPathEnum.TITLE_TABLE3, i).evaluate(page, XPathConstants.STRING);

                    wikiStamps.add(new WikiStamp(getWikiImageContent(imageSrc), author, null, circulation, catalogNumber, title, denomination));
                }
            }


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return wikiStamps;
    }

    private void initExprPathWikiStamp(int numberStampFirstTable, int numberStampSecondTable, int numberStampThirdTable) {
        for (int i = 2; i <= numberStampFirstTable; i++){
            getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE1, i);
            getExprXpath(ExprXPathEnum.IMG_SRC_TABLE1, i);
            getExprXpath(ExprXPathEnum.AUTHOR_TABLE1, i);
            getExprXpath(ExprXPathEnum.CIRCULATION_TABLE1, i);
            getExprXpath(ExprXPathEnum.DENOMINATION_TABLE1, i);
            getExprXpath(ExprXPathEnum.TITLE_TABLE1, i);
        }

        //second table
        for (int i = 2; i <= numberStampSecondTable; i++){
            getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE2, i);
            getExprXpath(ExprXPathEnum.IMG_SRC_TABLE2, i);
            getExprXpath(ExprXPathEnum.AUTHOR_TABLE2, i);
            getExprXpath(ExprXPathEnum.CIRCULATION_TABLE2, i);
            getExprXpath(ExprXPathEnum.DENOMINATION_TABLE2, i);
            getExprXpath(ExprXPathEnum.TITLE_TABLE2, i);
        }

        //Third table
        for (int i = 2; i <= numberStampThirdTable; i++){
            getExprXpath(ExprXPathEnum.CATALOG_NUMBER_TABLE3, i);
            getExprXpath(ExprXPathEnum.IMG_SRC_TABLE3, i);
            getExprXpath(ExprXPathEnum.AUTHOR_TABLE3, i);
            getExprXpath(ExprXPathEnum.CIRCULATION_TABLE3, i);
            getExprXpath(ExprXPathEnum.DENOMINATION_TABLE3, i);
            getExprXpath(ExprXPathEnum.TITLE_TABLE3, i);
        }

    }

    private byte[] getWikiImageContent(String imgSrc){
        Matcher imgSrcMatcher = GET_HTTP_IMG_URL.matcher(imgSrc);
        if(imgSrcMatcher.find()){
            return imageGrabber.grab("https://" + imgSrcMatcher.group());
        }

        return null;
    }

    private String getStampCatalogNumber(String stampNumber){
        Matcher stampNumberMatcher = GET_STAMP_NUMBER.matcher(stampNumber);
        List<String> catalogNumbers = new ArrayList<String>();
        while (stampNumberMatcher.find()){
            catalogNumbers.add(stampNumberMatcher.group());
        }

        if(catalogNumbers.size() == 0) return null;

        if(catalogNumbers.size() > 1){
            return catalogNumbers.get(0) + "-" + catalogNumbers.get(catalogNumbers.size()-1);
        } else {
            return catalogNumbers.get(0);
        }
    }


    private XPathExpression getExprXpath(ExprXPathEnum exprXPathEnum, int rowNumber) {
        List<XPathExpression> pathExpressionList = exprXpathMap.get(exprXPathEnum);

        if(pathExpressionList.size() <= rowNumber - 2) {
           pathExpressionList.add(initXPathExperession(exprXPathEnum, rowNumber));
        }
        return pathExpressionList.get(rowNumber - 2);
    }

    private void initExpressionMap(){
        if(exprXpathMap.size() == 0){
            exprXpathMap.put(ExprXPathEnum.IMG_SRC_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.IMG_SRC_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.IMG_SRC_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.AUTHOR_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.AUTHOR_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.AUTHOR_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.TITLE_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.TITLE_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.TITLE_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.DENOMINATION_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.DENOMINATION_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.DENOMINATION_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CIRCULATION_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CIRCULATION_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CIRCULATION_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CATALOG_NUMBER_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CATALOG_NUMBER_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.CATALOG_NUMBER_TABLE3, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.PUBLISH_DATE_TABLE1, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.PUBLISH_DATE_TABLE2, new ArrayList<XPathExpression>());
            exprXpathMap.put(ExprXPathEnum.PUBLISH_DATE_TABLE3, new ArrayList<XPathExpression>());

        }
    }

    private XPathExpression initXPathExperession(ExprXPathEnum exprXPathEnum, Integer rowNumber){
        XPathExpression xPathExpression = null;
        String expression = null;
        switch (exprXPathEnum){
            case IMG_SRC_TABLE1: case IMG_SRC_TABLE2: case IMG_SRC_TABLE3:
                expression = wikiStampParserConfig.getxPathImageSrc();
                break;
            case AUTHOR_TABLE1: case AUTHOR_TABLE2: case AUTHOR_TABLE3:
                expression = wikiStampParserConfig.getxPathDesign();
                break;
            case TITLE_TABLE1: case TITLE_TABLE2: case TITLE_TABLE3:
                expression = wikiStampParserConfig.getxPathTitle();
                break;
            case CIRCULATION_TABLE1: case CIRCULATION_TABLE2: case CIRCULATION_TABLE3:
                expression = wikiStampParserConfig.getxPathCirculation();
                break;
            case PUBLISH_DATE_TABLE1: case PUBLISH_DATE_TABLE2: case PUBLISH_DATE_TABLE3:
                expression = wikiStampParserConfig.getxPathPublisDate();
                break;
            case DENOMINATION_TABLE1: case DENOMINATION_TABLE2: case DENOMINATION_TABLE3:
                expression = wikiStampParserConfig.getxPathDenomination();
                break;
            case CATALOG_NUMBER_TABLE1: case CATALOG_NUMBER_TABLE2: case CATALOG_NUMBER_TABLE3:
                expression = wikiStampParserConfig.getxPathCatalogNumber();
                break;
        }

        expression = expression.replaceFirst("_N_", exprXPathEnum.getValue().toString()).replaceFirst("_X_", rowNumber.toString());

        try {
            xPathExpression = xpath.compile(expression);
        } catch (XPathExpressionException e) {
            logger.error("Error during compile expression");
        }

        return xPathExpression;
    }


    public Integer getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(Integer numberPages) {
        this.numberPages = numberPages;
    }

}
