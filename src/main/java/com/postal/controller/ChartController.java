package com.postal.controller;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;
import com.postal.model.stamp.StampType;
import com.postal.model.user.User;
import com.postal.service.stamp.StampService;
import com.postal.service.user.UserService;
import com.postal.service.user.UserStampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brakhar on 23.08.15.
 */

@Controller
public class ChartController {

    @Autowired
    private UserStampService userStampService;

    @Autowired
    private StampService stampService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/chart/stampChart.do", method = RequestMethod.GET)
    private ModelAndView drawPieChart(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("chartPage");

        model.addAttribute("blockNumberUrl", getPierChartByBlocks().toURLString());

        model.addAttribute("blockQuantityUrl", getPierChartByQuantityBlocks().toURLString());

        return modelAndView;
    }



    private PieChart getPierChartByBlocks(){
        List<Slice> slices = new ArrayList<Slice>();
        Integer totalNumberBlock = stampService.getTotalNumber(StampType.BLOCK);
        Integer boughtNumberBlock = userStampService.getBoughtUniqueTotalNumber(StampType.BLOCK, getUser().getUserName());;

        Slice bought = Slice.newSlice((boughtNumberBlock * 100) / totalNumberBlock, Color.ORANGE, null, "You have(" + boughtNumberBlock + ")");
        Slice toBuy = Slice.newSlice(((totalNumberBlock - boughtNumberBlock) * 100) / totalNumberBlock, Color.RED, null, "You should buy(" + (totalNumberBlock - boughtNumberBlock) + ")");

        slices.add(bought);
        slices.add(toBuy);

        return createPieChart("Information about unique blocks.(You have " + boughtNumberBlock + " unique blocks)", slices);
    }

    private PieChart getPierChartByQuantityBlocks(){
        int maxBlockQuantity = userStampService.getMaxBlockQuantity(getUser().getUserName());
        int[] arrayQuantities = new int[maxBlockQuantity];
        int totalQuantyties = 0;
        List<Slice> slices = new ArrayList<Slice>();


        for (int i = 0; i < maxBlockQuantity; i++){
            int numberByQuantity = getTotalNumberBlocksByQuantity(i+1);
            if (numberByQuantity != 0){
                totalQuantyties += arrayQuantities[i] = getTotalNumberBlocksByQuantity(i+1);
            }
        }

        for (int i = 0; i < maxBlockQuantity; i++){
            if(arrayQuantities[i] != 0) {
                slices.add(Slice.newSlice((arrayQuantities[i] * 100) / totalQuantyties, getRandomColor(), null, "Number of blocks with quantity - " + (i + 1) + " is " + arrayQuantities[i]));
            }
        }

        return createPieChart("Information about quantity of blocks.(You have " + totalQuantyties + " blocks at all.)", slices);
    }

    private Color getRandomColor() {
        String[] letters = "0123456789ABCDEF".split("");
        String color = "";
        for (int i = 0; i < 6; i++ ) {
            color += letters[((int) Math.round(Math.random() * 15))];
        }
        return Color.newColor(color);
    }


    private Integer getTotalNumberBlocksByQuantity(int quantity) {
        return userStampService.getTotalNumberBlocksByQuantity(quantity, getUser().getUserName());
    }

    private PieChart createPieChart(String title, List<Slice> slices){
        PieChart pieChart = GCharts.newPieChart(slices);
        pieChart.setTitle(title, Color.BLACK, 15);
        pieChart.setSize(720, 360);
        pieChart.setThreeD(true);

        return pieChart;
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


}
