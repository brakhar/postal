package com.postal.controller;

import com.postal.model.stamp.Category;
import com.postal.model.stamp.Stamp;
import com.postal.exception.PostalRepositoryException;
import com.postal.form.StampCollatorForm;
import com.postal.service.stamp.CategoryService;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import com.postal.service.stamp.StampURLCollatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by brakhar on 04.04.15.
 */

@Controller
public class StampURLCollatorController {

    final static Logger logger = LoggerFactory.getLogger(StampURLCollatorController.class);

    @Autowired
    private StampURLCollatorService stampURLCollatorService;

    @Autowired
    private StampService stampService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryService categoryService;

    private Map<Long, String> existedCategories = new HashMap<Long, String>();

    @Value("${hostForDownload.bigStampImg}")
    private String bigImageHost;

    @RequestMapping(value = "/grabStamp/addPageURL.do", method = RequestMethod.GET)
    public ModelAndView grabStampByPageURL(){
        ModelAndView modelAndView = new ModelAndView("collatorAddPageURL");
        modelAndView.addObject("stampCollatorForm", new StampCollatorForm());
        return modelAndView;
    }

    @RequestMapping(value = "/grabStamp/importStamp.do", method = RequestMethod.POST)
    public ModelAndView grabbingStampByPageURL(@ModelAttribute("stampCollatorForm") StampCollatorForm stampCollatorForm,
                                               BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("collatorResultAddPageURL");
        List<Stamp> result = new ArrayList<Stamp>();
        String urlForGrab = null;

        try {
            urlForGrab = URLDecoder.decode(stampCollatorForm.getPageURL(), "UTF-8");

            if (urlForGrab != null && urlForGrab.length() > 0){
                stampURLCollatorService.setPageUrl(urlForGrab);
                stampURLCollatorService.setBigImageHost(bigImageHost);
                result = stampURLCollatorService.process();
                saveStamp(result);
            }

        } catch (UnsupportedEncodingException e) {
            logger.error("Error decoding url for grabbing", e);
        }

        modelAndView.addObject("stampList", result);
        return modelAndView;
    }

    private void saveStamp(List<Stamp> stamps){
        initExistedCategorySet();
        Set<Category> updatedCategorySet = new HashSet<>();
        Category savedCategory = null;

        for (Stamp stamp: stamps){
            updatedCategorySet = new HashSet<>();
/*
            if (stamp.getCategories() != null && stamp.getCategories().size() > 0){
                for (Category category: stamp.getCategories()){
                    String categoryName = category.getName().trim();
                    if (!existedCategories.containsValue(categoryName)){
                        try {
                            savedCategory = categoryService.insert(category);
                            existedCategories.put(savedCategory.getId(), savedCategory.getName());
                            updatedCategorySet.add(savedCategory);
                        }catch (PostalRepositoryException ex){
                            logger.error("Error saving category. " + ex);
                        }
                    } else {
                        savedCategory = getSavedCategory(categoryName);
                        if (savedCategory != null){
                            updatedCategorySet.add(savedCategory);
                        }
                    }
                }
            }
            stamp.setCategories(updatedCategorySet);
*/
            try {
                stampService.insert(stamp);
            } catch (PostalRepositoryException e) {
                logger.error("Error saving stamp." + e);
            }
        }
    }

    private Category getSavedCategory(String categoryName){
        for (Map.Entry<Long, String> entry: existedCategories.entrySet()){
            if(entry.getValue().equals(categoryName)){
                return categoryService.getEntityById(entry.getKey());
            }
        }
        return null;
    }

    private void initExistedCategorySet(){
        existedCategories = new HashMap<>();
        for (Category category: categoryService.getList()){
            existedCategories.put(category.getId(), category.getName());
        }
    }

    public void setStampURLCollatorService(StampURLCollatorService stampURLCollatorService) {
        this.stampURLCollatorService = stampURLCollatorService;
    }

    public StampURLCollatorService getStampURLCollatorService() {
        return stampURLCollatorService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setStampService(StampService stampService) {
        this.stampService = stampService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }
}
