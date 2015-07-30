package com.postal.controller;

import com.postal.model.stamp.Country;
import com.postal.exception.PostalRepositoryException;
import com.postal.service.stamp.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by brakhar on 02.03.15.
 */


@Controller
public class CountryController {

    final static Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @RequestMapping(value="/country/add.do", method = RequestMethod.GET)
    public ModelAndView addCountryPage(){
        ModelAndView modelAndView = new ModelAndView("addCountry");
        modelAndView.addObject("country", new Country());

        return modelAndView;
    }

    @RequestMapping(value="/country/add.do", method = RequestMethod.POST)
    public ModelAndView addingCountry(@ModelAttribute Country country, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("addCountry");
        session.setAttribute("message", "Insert country successful.");
        try {
            countryService.insert(country);
        }catch (PostalRepositoryException ex){
            logger.error("Error savin country. " + ex);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/country/list.do", method = RequestMethod.GET)
    public ModelAndView listOfCountrys() {
        ModelAndView modelAndView = new ModelAndView("countryList");
        List<Country> countryList = countryService.getList();
        modelAndView.addObject("countryList", countryList);

        return modelAndView;
    }


    @RequestMapping(value = "/country/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editCountry(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("editCountry");

        Country country = countryService.getEntityById(id);
        modelAndView.addObject("country", country);
        return modelAndView;
    }

    @RequestMapping(value = "/country/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editingCountry(@ModelAttribute Country country, @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/country/list.do");

        try {
            countryService.update(country);
        } catch (PostalRepositoryException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}
