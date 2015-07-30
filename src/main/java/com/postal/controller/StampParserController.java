package com.postal.controller;

import com.postal.service.parser.StampSiteParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by brakhar on 01.07.15.
 */

@Controller
public class StampParserController {

    @Autowired
    private StampSiteParser stampSiteParser;

    @RequestMapping(value = "/stamp/parsing.do", method = RequestMethod.GET)
    public ModelAndView parseSite(@RequestParam(defaultValue = "true") boolean isFirstTime,
                                  @RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = "0") long stampId){
        ModelAndView modelAndView = new ModelAndView("parsingResultPage");

        modelAndView.addObject("result", stampSiteParser.parseSite(isFirstTime, pageNumber, stampId));
        return modelAndView;
    }
}
