package com.postal.service.parser;

import com.postal.model.stamp.Stamp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by brakhar on 26.06.15.
 */

public class StampSiteParserTest{

    private StampSiteParser stampSiteParser;

    @Mock
    private StampSiteConfig stampSiteConfig;
    private Stamp testStamp;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        setMockValues();

        setTestStampValues();

        stampSiteParser = new StampSiteParser();
        stampSiteParser.setStampSiteConfig(stampSiteConfig);
        stampSiteParser.initXpathExpressions();
    }



    @Test
    public void shouldReturnArrayOfPageNumbers(){
        List<Integer> pageNumbers = stampSiteParser.getPageNumbers();
        assertTrue(pageNumbers.size() > 0);
        assertEquals(pageNumbers.get(0), new Integer(1));
        assertEquals(pageNumbers.get(30), new Integer(31));
    }

    @Test
    public void shouldReturnStampIdFromStampImage(){
        Long expectedStampId = 1094373L;
        String imgSrc = "shop_pict.php?aid=1&cid=" + expectedStampId;
        Long stampId = stampSiteParser.getStampIdByStampImageSrc(imgSrc);
        assertEquals(expectedStampId, stampId);
    }

    @Test
    public void shouldReturnListStampImageSrc(){
        List<String> stampImageSrcs = stampSiteParser.getStampImageSrcs(1);
        assertTrue(stampImageSrcs.size() > 0);
        assertTrue(stampImageSrcs.get(0).startsWith("shop_pict.php?aid=1&cid="));
    }

    @Test
    public void shouldReturnBlockStampImgSrcByStampId(){
        String blockStampImgSrc = stampSiteParser.getBlockStampImageSrc(1190092L);
        assertEquals("shop_pict.php?aid=2&cid=1190092", blockStampImgSrc);
    }

    @Test
    public void shouldGenerateStampWithAllInformation(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2015, 5, 5);
        Stamp stamp = stampSiteParser.getFullStampInfoWithoutBlockAndSmallImageSrc(1190092L);

        assertEquals(testStamp.getTitle(), stamp.getTitle());
        assertEquals(calendar.getTime(), stamp.getPublishDate());
        assertEquals(testStamp.getCatalogNumber(), stamp.getCatalogNumber());
        assertEquals(testStamp.getFormat(), stamp.getFormat());
        assertEquals(testStamp.getDenomination(), stamp.getDenomination());
//        assertEquals(testStamp.getPerforation(), stamp.getPerforation());
        assertEquals(testStamp.getNumberStampInPiecePaper(), stamp.getNumberStampInPiecePaper());
        assertEquals(testStamp.getCirculation(), stamp.getCirculation());
        assertEquals(testStamp.getDesign(), stamp.getDesign());
       // assertEquals(testStamp.getTypePublish(), stamp.getTypePublish());
//        assertEquals(testStamp.getSpecialNotes(), stamp.getSpecialNotes());
//        assertEquals(testStamp.getOriginOfPublish(), stamp.getOriginOfPublish());

    }

    private void setMockValues() {
        Properties props = getPropertiesFile();

        when(stampSiteConfig.getSiteURL()).thenReturn("http://poshta.kiev.ua/nishop.php?act=shgr&id=1");
        when(stampSiteConfig.getStampPageURL()).thenReturn("http://poshta.kiev.ua/nishop.php?act=shcomm&gid=1&id=");
        when(stampSiteConfig.getStampListPageURL()).thenReturn("http://poshta.kiev.ua/nishop.php?act=shgr&id=1&page=");

        when(stampSiteConfig.getxPathPaginationAllPageNumbers()).thenReturn("/html/body/div[@class='mt'][2]/a");
        when(stampSiteConfig.getxPathAllSmallImageSrcByPage()).thenReturn("/html/body/table[@class='mt']/tbody/tr/td[1]/img/@src");
        when(stampSiteConfig.getxPathBlockImageSrc()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[1]/img/@src");
        when(stampSiteConfig.getxPathStampTitle()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[@class='commt']");
        when(stampSiteConfig.getxPathPublishDateWithText()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[2]");
        when(stampSiteConfig.getxPathStampNumberWithText()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[3]");
        when(stampSiteConfig.getxPathStampFullDescriptionNew()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[4]");
        when(stampSiteConfig.getxPathStampFullDescriptionOld()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[4]");
        when(stampSiteConfig.getRegexFullDescriptionOldHtml()).thenReturn("/html/body/table[4]/tbody/tr[2]/td[2]/div[4]");

        when(stampSiteConfig.getParsingKeyWordsFormat()).thenReturn(props.getProperty("parsing.keyWords.format"));
        when(stampSiteConfig.getParsingKeyWordsDenominationStamp()).thenReturn(props.getProperty("parsing.keyWords.denominationStamp"));
        when(stampSiteConfig.getParsingKeyWordsNumberStampInPiecePaper()).thenReturn(props.getProperty("parsing.keyWords.numberStampInPiecePaper"));
        when(stampSiteConfig.getParsingKeyWordsCirculation()).thenReturn(props.getProperty("parsing.keyWords.circulation"));
        when(stampSiteConfig.getParsingKeyWordsDesign()).thenReturn(props.getProperty("parsing.keyWords.design"));
        when(stampSiteConfig.getParsingKeyWordsTypePublish()).thenReturn(props.getProperty("parsing.keyWords.typePublish"));
        when(stampSiteConfig.getParsingKeyWordsSpecialNotes()).thenReturn(props.getProperty("parsing.test.specialNotes"));
        when(stampSiteConfig.getParsingKeyWordsPerforation()).thenReturn(props.getProperty("parsing.keyWords.perforation"));
        when(stampSiteConfig.getParsingKeyWordsSpecialNotes()).thenReturn(props.getProperty("parsing.keyWords.specialNotes"));
        when(stampSiteConfig.getParsingKeyWordsOriginOfPublish()).thenReturn(props.getProperty("parsing.keyWords.originOfPublish"));
        when(stampSiteConfig.getParsingKeyWordsSecurity()).thenReturn("");
        when(stampSiteConfig.getStampSiteConfig()).thenReturn(stampSiteConfig);
    }

    private Properties getPropertiesFile(){
        Properties props = new Properties();
        Reader reader = null;
        InputStream fileIn = getClass().getResourceAsStream("/parsing_test_data.properties");
        try {
            reader = new InputStreamReader(fileIn, "windows-1251");
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    private void setTestStampValues() {
        Properties props = getPropertiesFile();
        testStamp = new Stamp();
        testStamp.setCatalogNumber(props.getProperty("parsing.test.catalogNumber"));
        testStamp.setTitle(props.getProperty("parsing.test.title"));
        testStamp.setCirculation(props.getProperty("parsing.test.circulation"));
        testStamp.setFormat(props.getProperty("parsing.test.format"));
        testStamp.setDenomination(props.getProperty("parsing.test.denominationStamp"));
        testStamp.setDesign(props.getProperty("parsing.test.design"));
        testStamp.setOriginOfPublish(props.getProperty("parsing.test.originOfPublish"));
        testStamp.setTypePublish(props.getProperty("parsing.test.typePublish"));
        testStamp.setPerforation(props.getProperty("parsing.test.perforation"));
        testStamp.setSpecialNotes(props.getProperty("parsing.test.specialNotes"));
        testStamp.setNumberStampInPiecePaper(props.getProperty("parsing.test.numberStampInPiecePaper"));

    }

}
