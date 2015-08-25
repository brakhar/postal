package com.postal.controller;

import com.postal.model.stamp.Stamp;
import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.service.user.UserService;
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
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by brakhar on 13.06.15.
 */
public abstract class AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private UserStampService userStampService;

    @Autowired
    private UserService userService;


    protected PageRequest getPageRequest(HttpServletRequest request) {
        int pageNumber = 0;

        if(request.getParameter("iDisplayStart") != null){
            pageNumber = Integer.parseInt(request.getParameter("iDisplayStart"));
        }

        int pageDisplayLength = 0;

        if (request.getParameter("iDisplayLength") != null){
            pageDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
        }

        pageNumber = (pageNumber + pageDisplayLength) / pageDisplayLength -1 ;

        return new PageRequest(pageNumber, pageDisplayLength);
    }

    protected Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected User getUser(){
        Authentication auth = getAuthentication();
        String name = auth.getName();

        User authorizedUser = userService.getEntityById(name);
        return authorizedUser;
    }


    protected boolean isInteger(String checkValue){
        try {
            Integer.parseInt(checkValue);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    protected boolean isEmpty(String checkingString){
        if (checkingString != null && checkingString.trim().length() > 0) return false;
        return true;
    }

    protected UserStamp getUserStamp(Long stampId){
        Authentication auth = getAuthentication();

        if(auth != null && !auth.getName().contains("anonym")) {
           return userStampService.findByUserNameStampId(auth.getName(), stampId);
        }
        return null;
    }

    protected JSONObject getJSONTableData(Page tablePage, JSONArray jsonList){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("iTotalDisplayRecords", tablePage.getTotalElements());
            jsonObject.put("iTotalRecords", tablePage.getTotalElements());
            jsonObject.put("aaData", jsonList);
        } catch (JSONException e) {
            logger.error("Error during prepare data for table.", e);
        }
        return jsonObject;
    }

    protected JSONObject getJsonStamp(Stamp stamp, UserStamp userStamp) throws JSONException {
        JSONObject jsonStamp;

        jsonStamp = new JSONObject();
        jsonStamp.put("id", stamp.getId());
        if(stamp.isBlock() || stamp.isSmallPaper()){

            jsonStamp.put("catalogNumber", addBlockSmallPaperPrefix(stamp));
        } else {
            jsonStamp.put("catalogNumber", stamp.getCatalogNumber() != null ? stamp.getCatalogNumber() : "");
        }
        jsonStamp.put("stampImgId", stamp.getStampImageId());
        jsonStamp.put("title", stamp.getTitle());
        if (stamp.getPublishDate() != null){
            jsonStamp.put("dateEdition", formatter.format(stamp.getPublishDate()));
        } else {
            jsonStamp.put("dateEdition", "");
        }

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

        return jsonStamp;
    }

    protected String addBlockSmallPaperPrefix(Stamp stamp) {
        if (stamp.isBlock()){
            return "b" + stamp.getBlockNumber();
        } else {
            return "m" + stamp.getSmallPaperNumber();
        }
    }

    protected String getJSONUserStampList(Page<UserStamp> userStamps) throws JSONException {
        JSONArray jsonStampList = new JSONArray();

        for (UserStamp userStamp: userStamps.getContent()) {
            jsonStampList.put(getJsonStamp(userStamp.getStamp(), userStamp));
        }

        return getJSONTableData(userStamps, jsonStampList).toString();
    }

    protected String getJSONStampList(Page<Stamp> stampPage) throws JSONException {
        JSONArray jsonStampList = new JSONArray();
        for (Stamp stamp: stampPage.getContent()) {
            jsonStampList.put(getJsonStamp(stamp, getUserStamp(stamp.getId())));
        }

        return getJSONTableData(stampPage, jsonStampList).toString();
    }


}

