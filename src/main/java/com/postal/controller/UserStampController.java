package com.postal.controller;

import com.postal.exception.PostalRepositoryException;
import com.postal.model.stamp.Stamp;
import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.model.user.UserStampPK;
import com.postal.service.stamp.StampService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by brakhar on 13.06.15.
 */

@Controller
public class UserStampController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(UserStampController.class);

    @Autowired
    private UserStampService userStampService;

    @Autowired
    private UserService userService;

    @Autowired
    private StampService stampService;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @RequestMapping(value = "/stamp/loadUserStamps.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody
    String getUserStamps(HttpServletRequest request) throws JSONException {
        String searchValue = request.getParameter("sSearch");

        PageRequest pageRequest = getPageRequest(request);

        Authentication auth = getAuthentication();
        String name = auth.getName();

        User authorizedUser = userService.getEntityById(name);
        Integer yearValue = 0;

        Page<UserStamp> userStamps = userStampService.findByUser(pageRequest, authorizedUser);

        if(searchValue ==null || searchValue.trim().length() == 0){
            userStamps = userStampService.findByUser(pageRequest, authorizedUser);
        } else {
            if(isInteger(searchValue)){
                yearValue = Integer.parseInt(searchValue);
            }
            searchValue = "%" + searchValue + "%";
            userStamps = userStampService.findBySearchValue(searchValue, authorizedUser.getUserName(), yearValue, pageRequest);
        }

        return getJsonUserStampList(userStamps);
    }

    @RequestMapping(value = "/stamp/updateUserStampQuantity.do", method = RequestMethod.GET)
    private @ResponseBody Boolean changeUserStampQuantity(@RequestParam Long stampId,
                                                          @RequestParam Integer quantityType,
                                                          @RequestParam Integer quantity) throws JSONException {
        boolean result = true;
        boolean isPresent = true;
        Authentication auth = getAuthentication();
        String userName = auth.getName();
        UserStampPK userStampPK = new UserStampPK(auth.getName(), stampId);

        User user = userService.getEntityById(userName);
        Stamp stamp = stampService.getEntityById(stampId);
        UserStamp userStampUpdated = null;

        isPresent = userStampService.isExists(userStampPK);
        if(!isPresent) {
            userStampUpdated = new UserStamp();
            userStampUpdated.setStamp(stamp);
            userStampUpdated.setUser(user);
            userStampUpdated.setId(userStampPK);
        } else {
            userStampUpdated = userStampService.getEntityById(userStampPK);
        }

        switch (quantityType) {
            case 1:
                userStampUpdated.setFullListQuantity(quantity);
                break;
            case 2:
                userStampUpdated.setOneLineQuantity(quantity);
                break;
            case 3:
                userStampUpdated.setOnePieceQuantity(quantity);
                break;
        }

        if(userStampUpdated.getFullListQuantity() == 0 && userStampUpdated.getOneLineQuantity() == 0 && userStampUpdated.getOnePieceQuantity() == 0) {
            try {
                userStampService.delete(new UserStampPK(auth.getName(), stampId));
            } catch (PostalRepositoryException e) {
                logger.error("Error during removing user Stamp.", e);
                return false;
            }
            return true;
        }

        try {
            if (isPresent){
                userStampService.update(userStampUpdated);
            } else {
                userStampService.insert(userStampUpdated);
            }
        } catch (PostalRepositoryException e) {
            logger.error("Error during saving quantity of user's stamp.", e);
            result = false;
        }

        return result;
    }

    @RequestMapping(value = "/stamp/userStampList.do", method = RequestMethod.GET)
    public ModelAndView getUserStampListPage() {
        ModelAndView modelAndView = new ModelAndView("userStampList");
        return modelAndView;
    }

    @RequestMapping(value = "/stamp/addUserStamp/{stampId}/{fullListQuantity}/{oneLineQuantity}/{onePieceQuantity}.do", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    private @ResponseBody String addUserStamp(@PathVariable Long stampId,
                                              @PathVariable Integer fullListQuantity,
                                              @PathVariable Integer oneLineQuantity,
                                              @PathVariable Integer onePieceQuantity){

        JSONObject jsonObject = new JSONObject();
        boolean result = true;

        Authentication auth = getAuthentication();
        String name = auth.getName();

        User authorizedUser = userService.getEntityById(name);
        Stamp stampToSave = stampService.getEntityById(stampId);

        UserStamp userStamp = new UserStamp();
        userStamp.setUser(authorizedUser);
        userStamp.setStamp(stampToSave);
        userStamp.setFullListQuantity(fullListQuantity);
        userStamp.setOneLineQuantity(oneLineQuantity);
        userStamp.setOnePieceQuantity(onePieceQuantity);
        userStamp.setId(new UserStampPK(authorizedUser.getUserName(), stampToSave.getId()));
        try {
            userStampService.insert(userStamp);
        } catch (PostalRepositoryException e) {
            logger.error("Error during saving user's stamp in his collection!");
            result = false;
        }

        try {
            jsonObject.put("result", result);
        } catch (JSONException e) {
            logger.error("Error JSON transfotm. " + e.getMessage());
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/stamp/getQuantityByStampIdUserName")
    private @ResponseBody String getQuantityByStampIdAndUserName(@RequestParam Long stampId, @RequestParam String userName){

        //userService.find

        return null;
    }


    private String getJsonUserStampList(Page<UserStamp> userStamps) throws JSONException {
        JSONArray jsonStampList = new JSONArray();

        for (UserStamp userStamp: userStamps.getContent()) {
            jsonStampList.put(getJsonUserStamp(userStamp));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iTotalDisplayRecords", userStamps.getTotalElements());
        jsonObject.put("iTotalRecords", userStamps.getTotalElements());
        jsonObject.put("aaData", jsonStampList);
        return jsonObject.toString();
    }

    private JSONObject getJsonUserStamp(UserStamp userStamp) throws JSONException {
        JSONObject jsonStamp;
        JSONArray jsonArrayUserStampQuantityByStampType = new JSONArray();
        Authentication auth = getAuthentication();

        jsonStamp = new JSONObject();
        jsonStamp.put("id", userStamp.getStamp().getId());
        jsonStamp.put("catalogNumber", userStamp.getStamp().getCatalogNumber());
        jsonStamp.put("stampImgId",userStamp.getStamp().getStampImageId());
        jsonStamp.put("title", userStamp.getStamp().getTitle());
        jsonStamp.put("dateEdition", formatter.format(userStamp.getStamp().getPublishDate()));

        if(auth != null && !auth.getName().contains("anonym")){
            jsonStamp.put("flq", userStamp.getFullListQuantity());
            jsonStamp.put("olq", userStamp.getOneLineQuantity());
            jsonStamp.put("opq", userStamp.getOnePieceQuantity());
        }

        return jsonStamp;
    }
}
