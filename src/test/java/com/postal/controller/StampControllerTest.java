package com.postal.controller;

import com.postal.exception.PostalRepositoryException;
import com.postal.model.stamp.*;
import com.postal.service.stamp.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Addd by brakhar on 3/18/2015.
 */

public class StampControllerTest {

    private final static String COUNTRY_1 = "Ukraine";
    private final static String COUNTRY_3 = "French";

    private final static Long STAMP_ID_1 = 1L;
    private final static Long STAMP_ID_2 = 2L;

    private final static String STAMP_NUMBER_1 = "111/111";
    private final static String STAMP_NUMBER_2 = "222/222";

    private final static String STAMP_NAME_1 = "Stamp name 1";
    private final static String STAMP_NAME_2 = "Stamp name 2";

    private final static String STAMP_DESCRIPTION_1 = "Stamp description 1";
    private final static String STAMP_DESCRIPTION_2 = "Stamp description 2";

    private static final Integer STAMP_YEAR_1 = 1985;
    private static final Long STAMP_COUNTRY_ID_1 = 111L;
    private static final Long STAMP_CONDITION_ID_1 = 1L;
    public static final String URL_ADD_STAMP = "/stamp/add.do";
    public static final String PARAM_STAMP_NUMBER = "stampNumber";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_YEAR = "year";
    public static final String PARAM_COUNTRY = "country";
    public static final String PARAM_CATEGORIES = "categories";
    public static final String PARAM_FACE_VALUE = "faceValue";
    public static final String PARAM_HINGED = "hinged";
    public static final String PARAM_STAMP_CONDITION = "stampCondition";

    @Mock
    private StampService stampService;

    @Mock
    private CountryService countryService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private StampConditionService stampConditionService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private StampController stampController;

    @Mock
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    private List<Stamp> stampList;

    private List<Category> categoryList;

    private List<Country> countryList;

    private Image noEmptyImage;

    private MockMultipartFile imageFile;

    @Mock
    private Country country1;
    @Mock
    private StampCondition stampCondition1;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stampController).build();

        countryList = new ArrayList();
        categoryList = new ArrayList();

        stampList = new ArrayList();
/*
        stampList.add(new Stamp(STAMP_ID_1, STAMP_NAME_1, STAMP_DESCRIPTION_1));
        stampList.add(new Stamp(STAMP_ID_2, STAMP_NAME_2, STAMP_DESCRIPTION_2));
*/

        noEmptyImage = new Image();
        noEmptyImage.setId(111L);
        noEmptyImage.setContent(new byte[]{12, 12});

        imageFile = new MockMultipartFile("imageFile", "filename.jpg", null, new byte[]{123, 123, 123});
    }

    @Test @Ignore
    public void shouldAddStamp() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors());
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutStampNumber() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_STAMP_NUMBER));
    }


    @Test @Ignore
    public void shouldNotAddStampWithoutStampName() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_NAME));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutStampDescription() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_DESCRIPTION));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutYear() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_YEAR));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutCategories() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_CATEGORIES));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutCountry() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_COUNTRY));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutFaceValue() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_FACE_VALUE));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutHinged() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_HINGED));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutStampCondition() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(fileUpload(URL_ADD_STAMP, "image").file(imageFile)
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", PARAM_STAMP_CONDITION));
    }

    @Test @Ignore
    public void shouldNotAddStampWithoutImageFile() throws Exception {
        setMockValuesForServices();

        this.mockMvc.perform(post(URL_ADD_STAMP, "image")
                        .param(PARAM_STAMP_NUMBER, STAMP_NUMBER_1)
                        .param(PARAM_NAME, STAMP_NAME_1)
                        .param(PARAM_DESCRIPTION, STAMP_DESCRIPTION_1)
                        .param(PARAM_YEAR, STAMP_YEAR_1.toString())
                        .param(PARAM_CATEGORIES, "111,222")
                        .param(PARAM_COUNTRY, STAMP_COUNTRY_ID_1.toString())
                        .param(PARAM_FACE_VALUE, "50c")
                        .param(PARAM_HINGED, Boolean.FALSE.toString())
                        .param(PARAM_STAMP_CONDITION, STAMP_CONDITION_ID_1.toString())
        )
                .andExpect(status().isOk())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("stampForm", "imageFile"));
    }


    private void setMockValuesForServices() throws PostalRepositoryException {
        when(imageService.insertImage(any(Image.class))).thenReturn(noEmptyImage.getId());
        when(stampService.insert(any(Stamp.class))).thenReturn(new Stamp());
        when(countryService.getEntityById(STAMP_COUNTRY_ID_1)).thenReturn(country1);
        when(stampConditionService.getEntityById(STAMP_CONDITION_ID_1)).thenReturn(stampCondition1);
    }

    @Test @Ignore
    public void shouldNotAddStamp() throws Exception{
        setMockValuesForServices();
        when(bindingResult.hasErrors()).thenReturn(false);

        this.mockMvc.perform(post(URL_ADD_STAMP))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(11));

    }

    @Test @Ignore
    public void shouldShowFullStampList() throws Exception {
        when(stampService.getList()).thenReturn(stampList);
        when(bindingResult.hasErrors()).thenReturn(false);

        this.mockMvc.perform(get("/stamp/pages/1.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("stampList"))
                .andExpect(model().attribute("stampList", stampList));
    }
}
