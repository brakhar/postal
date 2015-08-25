package com.postal.controller;

import com.postal.exception.PostalRepositoryException;
import com.postal.form.StampForm;
import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import com.postal.validation.ErrorMessage;
import com.postal.validation.ValidationResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brakhar on 02.03.15.
 */


@Controller
public class StampController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(StampController.class);

    @Autowired
    private StampService stampService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/stamp/deleteStamp", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String deleteStamp(@RequestParam Long id) throws JSONException {
        JSONObject response = new JSONObject();

        if (id != null && id != 0){
            try {
                Stamp stampForDeleting = stampService.getEntityById(id);
                Long imageId = stampForDeleting.getStampImageId();
                Long bigImageId = stampForDeleting.getFullListImageId();

                stampService.delete(id);

                if(imageId != null){
                    imageService.deleteImageById(imageId);
                }

                if(bigImageId != null){
                    imageService.deleteImageById(bigImageId);
                }

                response.put("result", true);
            } catch (PostalRepositoryException e) {
                logger.error("Error during deleting stamp with ID=" + id);
                response.put("result", false);
            }
        }
        return response.toString();
    }

    @RequestMapping(value = "/stamp/loadStamp.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String loadStamp(HttpServletRequest request) throws IOException, JSONException {
        PageRequest pageRequest = getPageRequest(request);

        Page<Stamp> stampPage = null;
        Integer yearValue = 0;

        String searchValue = request.getParameter("sSearch");

        String searchByCatalogNumber = request.getParameter("sSearch_1");
        String searchByTitle = isEmpty(request.getParameter("sSearch_3")) ? "" : "%" + request.getParameter("sSearch_3") + "%";
        Integer searchByYear = isEmpty(request.getParameter("sSearch_4")) ? 0 : Integer.parseInt(request.getParameter("sSearch_4"));

        if(!isEmpty(searchByCatalogNumber) || !isEmpty(searchByTitle) || searchByYear != 0){
            stampPage = stampService.findStamps(pageRequest, searchByCatalogNumber, searchByTitle, searchByYear);
        } else {
            if(searchValue ==null || searchValue.trim().length() == 0){
                stampPage = stampService.getListOrderedByPublishDate(pageRequest);
            } else {
                if(isInteger(searchValue)){
                    yearValue = Integer.parseInt(searchValue);
                }
                searchValue = "%" + searchValue + "%";
                stampPage = stampService.findBySearchValue(searchValue, yearValue, pageRequest);
            }
        }


        return getJSONStampList(stampPage);
    }

    @RequestMapping(value = "/stamp/add.do", method = RequestMethod.GET)
    private ModelAndView addStamp(){
        ModelAndView modelAndView = new ModelAndView("addStampPage");
        Stamp stamp = new Stamp();
        modelAndView.addObject("stamp", stamp);
        modelAndView.addObject("actionType", "add");
        return modelAndView;
    }

    @RequestMapping(value = "/stamp/edit/{id}.do", method = RequestMethod.GET)
    private ModelAndView editStamp(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("addStampPage");

        Stamp stamp = stampService.getEntityById(id);
        modelAndView.addObject("stamp", stamp);
        modelAndView.addObject("actionType", "edit");
        return modelAndView;
    }

    @RequestMapping(value = "/stamp/save.do", method = RequestMethod.POST)
    private @ResponseBody
    ValidationResponse saveStamp(@Valid @ModelAttribute Stamp stamp, BindingResult result){
        ValidationResponse res = new ValidationResponse();

        if(result.hasErrors()){
            res.setStatus("FAIL");
            List<FieldError> allErrors = result.getFieldErrors();
            List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
            for (FieldError objectError : allErrors) {
                errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
            }
            res.setResult(errorMesages);
        } else {
            res.setStatus("SUCCESS");
            try {
                if(stamp.getId() != null){
                    stampService.update(stamp);
                } else {
                    stampService.insert(stamp);
                }
            } catch (PostalRepositoryException e) {
                logger.error("Error saving stamp.", e);
            }
        }
        return res;
    }

    @RequestMapping(value = "/stamp/list.do", method = RequestMethod.GET)
    private ModelAndView getStampListPage() {
        ModelAndView modelAndView = new ModelAndView("stampList");

        StampForm stampForm = new StampForm();

        modelAndView.addObject("stampForm", stampForm);

        return modelAndView;
    }

}
