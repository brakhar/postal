package com.postal.controller;

import com.postal.model.stamp.Country;
import com.postal.service.stamp.CountryService;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by brakhar on 3/18/2015.
 */

public class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private MockMvc mockMvc;

    private List<Country> countryList;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();

        countryList = new ArrayList();
        countryList.add(new Country(12L, "Ukraine"));
        countryList.add(new Country(13L, "France"));
    }

    @Test @Ignore
    public void shouldCreateNewCountry() throws Exception {
        when(countryService.insert(any(Country.class))).thenReturn(any(Country.class));

        this.mockMvc.perform(post("/country/add.do")
                .param("name", "Ukraine"))
                .andExpect(status().isOk());

    }

    @Test @Ignore
    public void shouldShowFullCountryList() throws Exception {
        when(countryService.getList()).thenReturn(countryList);

        this.mockMvc.perform(get("/country/list.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("countryList"))
                .andExpect(model().attribute("countryList", countryList));
    }
}
