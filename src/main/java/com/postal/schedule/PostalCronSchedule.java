package com.postal.schedule;

import com.postal.service.parser.StampSiteParser;
import com.postal.service.parser.WikiStampParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 7/8/2015.
 */

@Component
@EnableScheduling
public class PostalCronSchedule {

    private boolean isRun = false;

    @Autowired
    private StampSiteParser stampSiteParser;

    @Autowired
    private WikiStampParserService wikiStampParserService;

    @Scheduled(cron = "00 40 23 * * *")
    public void startCronJob(){
        stampSiteParser.parseSite(false, 1, 0);
    }

//    @Scheduled(fixedDelay = 60000)
    public void startFixedDelay(){
        if(!isRun){
            isRun = true;
            for (int i = 2002; i <= 2002; i++){
                System.out.println("Year - " + i);
                wikiStampParserService.updateStampByYear(i);
            }
        }

    }

}
