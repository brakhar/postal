package com.postal.controller;

import com.postal.model.stamp.Category;
import com.postal.exception.PostalRepositoryException;
import com.postal.service.stamp.CategoryService;
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
public class CategoryController {

    final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value="/category/add.do", method = RequestMethod.GET)
    public ModelAndView addCategoryPage(){
        ModelAndView modelAndView = new ModelAndView("addCategory");
        modelAndView.addObject("category", new Category());

        return modelAndView;
    }

    @RequestMapping(value="/category/add.do", method = RequestMethod.POST)
    public ModelAndView addingCategory(@ModelAttribute Category category, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("addCategory");
        session.setAttribute("message", "Insert category successful.");
        try {
            categoryService.insert(category);
        }catch (PostalRepositoryException ex){
            logger.error("Error during savin category ." + ex);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/category/list.do", method = RequestMethod.GET)
    public ModelAndView listOfCategorys() {
        ModelAndView modelAndView = new ModelAndView("categoryList");
        List<Category> CategoryList = categoryService.getList();
        modelAndView.addObject("categoryList", CategoryList);

        return modelAndView;
    }


    @RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editCategory(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("editCategory");

        Category category = categoryService.getEntityById(id);
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @RequestMapping(value = "/category/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editingCategory(@ModelAttribute Category category, @PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/category/list.do");

        try {
            categoryService.update(category);
        } catch (PostalRepositoryException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}
