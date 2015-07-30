package com.postal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by brakhar on 09.03.15.
 */

@Controller
@RequestMapping(value = "/home.do")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

}
