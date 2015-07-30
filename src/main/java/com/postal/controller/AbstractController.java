package com.postal.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by brakhar on 13.06.15.
 */
public abstract class AbstractController {

    PageRequest getPageRequest(HttpServletRequest request) {
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

    Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    boolean isInteger(String checkValue){
        try {
            Integer.parseInt(checkValue);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

}

