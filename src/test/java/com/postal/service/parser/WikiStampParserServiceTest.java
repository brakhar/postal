package com.postal.service.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by brakhar on 22.07.15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WikiStampParserServiceTestConfig.class})
public class WikiStampParserServiceTest {

    public static final int YEAR = 1992;

    private WikiStampParserService wikiStampParserService;

    private WikiStampParserConfig wikiStampParserConfig;

    @Autowired
    public void setWikiStampParserConfig(WikiStampParserConfig wikiStampParserConfig) {
        this.wikiStampParserConfig = wikiStampParserConfig;
    }

    @Autowired
    public void setWikiStampParserService(WikiStampParserService wikiStampParserService) {
        this.wikiStampParserService = wikiStampParserService;
    }

    @Before
    public void setUp(){
        //wikiStampParserService.init(wikiStampParserConfig);
    }

    @Test
    public void testGettingAllYearsOfPublishStamps(){
        List<Integer> years = wikiStampParserService.getYears();
        int numberYears = Calendar.getInstance().get(Calendar.YEAR) - YEAR + 1;

        assertEquals(numberYears, years.size());
    }

    @Test
    public void testGettingCountStampBySomePage(){
        int numberStamp = wikiStampParserService.getFullNumberStampByYear(YEAR);
        assertEquals(25, numberStamp);
    }

    @Test
    public void testGettingStampInfoListByPage(){
        List<WikiStamp> stamps = wikiStampParserService.getStampInfoList(YEAR);
        assertEquals(25, stamps.size());

    }
}
