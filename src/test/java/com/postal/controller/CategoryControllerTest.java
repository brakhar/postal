package com.postal.controller;

import com.postal.model.stamp.Category;
import com.postal.service.stamp.CategoryService;
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

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    private List<Category> categoryList;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        categoryList = new ArrayList();
        categoryList.add(new Category(12L, "Nature"));
        categoryList.add(new Category(13L, "Ships"));
    }

    @Test @Ignore
    public void shouldCreateNewCategory() throws Exception {
        when(categoryService.insert(any(Category.class))).thenReturn(any(Category.class));

        this.mockMvc.perform(post("/category/add.do")
                .param("name", "Ukraine  "))
                .andExpect(status().isOk());

    }

    @Test @Ignore
    public void shouldShowFullCategoryList() throws Exception {
        when(categoryService.getList()).thenReturn(categoryList);

        this.mockMvc.perform(get("/category/list.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("categoryList"))
                .andExpect(model().attribute("categoryList", categoryList));
    }
}
