package com.postal.controller;

import com.postal.model.stamp.Stamp;
import com.postal.service.stamp.CategoryService;
import com.postal.service.stamp.ImageService;
import com.postal.service.stamp.StampService;
import com.postal.service.stamp.StampURLCollatorService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by brakhar on 03.04.15.
 */
public class StampURLCollatorControllerTest {

    private static final String PAGE_URL = "http://localhost:8080/stamp/page1";

    @InjectMocks
    private StampURLCollatorController stampURLCollatorController;

    @Mock
    private StampURLCollatorService stampURLCollatorService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private StampService stampService;

    @Mock
    private ImageService imageService;

    private MockMvc mockMvc;

    @Mock
    private Stamp stamp1;

    private List<Stamp> stampList = new ArrayList<Stamp>();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stampURLCollatorController).build();
        stampURLCollatorController.setStampURLCollatorService(stampURLCollatorService);
        stampURLCollatorController.setCategoryService(categoryService);
        stampURLCollatorController.setStampService(stampService);
        stampURLCollatorController.setImageService(imageService);

        stampList.add(stamp1);
    }

    @Test @Ignore
    public void shouldGrabStampsByPageURL() throws Exception {
        when(stampURLCollatorService.process()).thenReturn(stampList);
        mockMvc.perform(post("/grabStamp/importStamp.do").param("pageURL", PAGE_URL))
                .andExpect(status().isOk());
    }
}
