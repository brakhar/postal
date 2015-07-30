package com.postal.controller;

import com.postal.exception.PostalRepositoryException;
import com.postal.form.StampForm;
import com.postal.model.stamp.Image;
import com.postal.model.stamp.Stamp;
import com.postal.model.stamp.StampDataTable;
import com.postal.model.user.UserStamp;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import com.postal.service.user.UserStampService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brakhar on 02.03.15.
 */


@Controller
public class StampController extends AbstractController{

    static final Logger logger = LoggerFactory.getLogger(StampController.class);

    private static final int PAGE_SIZE = 15;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private StampService stampService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserStampService userStampService;

    @RequestMapping(value = "/stamp/deleteStamp", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String deleteStamp(@RequestParam Long id) throws JSONException {
        JSONObject response = new JSONObject();

        if (id != null && id != 0){
            try {
                Stamp stampForDeleting = stampService.getEntityById(id);
                Long imageId = stampForDeleting.getStampImageId();
                Long bigImageId = stampForDeleting.getFullListImageId();

                stampService.delete(id);

                imageService.deleteImageById(imageId);
                imageService.deleteImageById(bigImageId);

                response.put("result", true);
            } catch (PostalRepositoryException e) {
                logger.error("Error during deleting stamp with ID=" + id);
                response.put("result", false);
            }
        }
        return response.toString();
    }

    @RequestMapping(value = "/stamp/loadStamp", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String loadStamp(HttpServletRequest request) throws IOException, JSONException {
        PageRequest pageRequest = getPageRequest(request);

        Page<Stamp> stampPage = null;
        Integer yearValue = 0;

        String searchValue = request.getParameter("sSearch");

        if(searchValue ==null || searchValue.trim().length() == 0){
            stampPage = stampService.findAll(pageRequest);
        } else {
            if(isInteger(searchValue)){
                yearValue = Integer.parseInt(searchValue);
            }
            searchValue = "%" + searchValue + "%";
            stampPage = stampService.findBySearchValue(searchValue, yearValue, pageRequest);
        }

        return getJSONStampList(stampPage);
    }

    @RequestMapping(value = "/stamp/toBuyStamp.do", method = RequestMethod.GET)
    private ModelAndView getToBuyStamp(){
        return new ModelAndView("toByStampPage");
    }

    @RequestMapping(value = "/stamp/loadToBuyUserStamps.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String getToBuyUserStamps(HttpServletRequest request) throws JSONException {
        Authentication authentication = getAuthentication();

        PageRequest pageRequest = getPageRequest(request);

        Page<Stamp> stamps = stampService.findByUserToBuyStamps(pageRequest, authentication.getName());
        return getJSONStampList(stamps);
    }


    @RequestMapping(value = "/stamp/add", method = RequestMethod.GET)
    private ModelAndView addStamp(){
        ModelAndView modelAndView = new ModelAndView("addStampPage");

        modelAndView.addObject("stamp", new Stamp());
        modelAndView.addObject("actionType", "add");
        return modelAndView;
    }

    @RequestMapping(value = "/stamp/edit/{id}", method = RequestMethod.GET)
    private ModelAndView editStamp(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("addStampPage");

        Stamp stamp = stampService.getEntityById(id);
        modelAndView.addObject("stamp", stamp);
        modelAndView.addObject("actionType", "edit");
        return modelAndView;
    }

    @RequestMapping(value = "/stamp/save.do", method = RequestMethod.POST, headers = "content-type=multipart/*", produces = "text/plain;charset=cp1251")
    private @ResponseBody ModelAndView saveStamp(@Valid @ModelAttribute Stamp stamp, BindingResult bindingResult,
                                                @RequestParam(value = "fullListImage", required = false) MultipartFile fullListImage,
                                                @RequestParam(value = "stampImage", required = false) MultipartFile stampImage
                                                ){
        ModelAndView modelAndView = new ModelAndView("savedStampPage");

        if(bindingResult.hasErrors()){
            return new ModelAndView("addStampPage");
        }

        try {

            if(stampImage != null && stampImage.getSize() != 0) {
                stamp.setStampImageId(imageService.insertImage(new Image(stampImage.getBytes())));
            }
            if(fullListImage != null && fullListImage.getSize() != 0) {
                stamp.setFullListImageId(imageService.insertImage(new Image(fullListImage.getBytes())));
            }
            if(stamp.getId() != null){
                stampService.update(stamp);
            } else {
                stampService.insert(stamp);
            }
        } catch (PostalRepositoryException e) {
            logger.error("Error saving stamp.", e);
        } catch (IOException e) {
            logger.error("Error saving image.", e);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/stamp/list.do", method = RequestMethod.GET)
    private ModelAndView getStampListPage() {
        ModelAndView modelAndView = new ModelAndView("stampList");

        StampForm stampForm = new StampForm();

        modelAndView.addObject("stampForm", stampForm);

        return modelAndView;
    }

    @RequestMapping(value = "/stamp/getStamp.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String getStampById(@RequestParam("stampId") Long stampId) throws JSONException {

        Stamp stamp = stampService.getEntityById(stampId);

        return getJsonStamp(stamp).toString();

    }

    private String getJSONStampList(Page<Stamp> stampPage) throws JSONException {

        JSONArray jsonStampList = new JSONArray();
        for (Stamp stamp: stampPage.getContent()) {
            jsonStampList.put(getJsonStamp(stamp));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iTotalDisplayRecords", stampPage.getTotalElements());
        jsonObject.put("iTotalRecords", stampPage.getTotalElements());
        jsonObject.put("aaData", jsonStampList);

        return jsonObject.toString();
    }

    private JSONObject getJsonStamp(Stamp stamp) throws JSONException {
        JSONObject jsonStamp;
        Authentication auth = getAuthentication();

        jsonStamp = new JSONObject();
        jsonStamp.put("id", stamp.getId());
        jsonStamp.put("catalogNumber", stamp.getCatalogNumber());
        jsonStamp.put("stampImgId", stamp.getStampImageId());
        jsonStamp.put("title", stamp.getTitle());
        jsonStamp.put("dateEdition", formatter.format(stamp.getPublishDate()));

        if(auth != null && !auth.getName().contains("anonym")){
            UserStamp userStamp = userStampService.findByUserNameStampId(auth.getName(), stamp.getId());
            if(userStamp!=null){
                jsonStamp.put("ub", 1);
                jsonStamp.put("flq", userStamp.getFullListQuantity());
                jsonStamp.put("olq", userStamp.getOneLineQuantity());
                jsonStamp.put("opq", userStamp.getOnePieceQuantity());
            } else {
                jsonStamp.put("ub", 0);
                jsonStamp.put("flq", 0);
                jsonStamp.put("olq", 0);
                jsonStamp.put("opq", 0);
            }
        }

        return jsonStamp;
    }

    private Long saveImage(MultipartFile image){
        try {
            if(image != null && image.getBytes().length > 0){
               return imageService.insertImage(new Image(image.getBytes()));
            }
        } catch (IOException e) {
            logger.error("Error saving image");
        }
        return null;
    }

    private List<StampDataTable> transform(List<Stamp> list){
        List<StampDataTable> stampDataTables = new ArrayList<StampDataTable>();
        for (Stamp stamp: list) {
//            stampDataTables.add(new StampDataTable(stamp.getName(), stamp.getYear(), stamp.getStampImageId()));
        }
        return stampDataTables;
    }

}
