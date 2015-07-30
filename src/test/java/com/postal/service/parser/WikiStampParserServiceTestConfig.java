package com.postal.service.parser;

import com.postal.service.stamp.ImageGrabber;
import com.postal.service.stamp.ImageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by brakhar on 22.07.15.
 */

@Configuration
@PropertySource("classpath:wiki-parser-config.properties")
public class WikiStampParserServiceTestConfig {

    @Bean
    WikiStampParserConfig wikiStampParserConfig(){
        return new WikiStampParserConfig();
    }

    @Bean
    WikiStampParserService wikiStampParserService(){
        return new WikiStampParserService();
    }

    @Bean
    ImageGrabber imageGrabber(){
        return new ImageGrabber();
    }

    @Bean
    ImageService imageService() {
        return new ImageService();
    }


}
