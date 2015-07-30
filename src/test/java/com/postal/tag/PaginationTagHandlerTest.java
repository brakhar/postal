package com.postal.tag;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

import static org.junit.Assert.assertTrue;

/**
 * Created by brakhar on 15.05.15.
 */

public class PaginationTagHandlerTest {

    private MockPageContext mockPageContext;
    private MockServletContext mockServletContext;

    private final String PAGE_URL = "http://site.com/pageContent";

    private final String PAGE_URL_WITH_NUMBER = PAGE_URL + "?pageNumber=";

    private final String PAGE_URL_1 = PAGE_URL_WITH_NUMBER + 1;
    private final String PAGE_URL_2 = PAGE_URL_WITH_NUMBER + 2;
    private final String PAGE_URL_3 = PAGE_URL_WITH_NUMBER + 3;
    private final String PAGE_URL_4 = PAGE_URL_WITH_NUMBER + 4;
    private final String PAGE_URL_5 = PAGE_URL_WITH_NUMBER + 5;
    private final String PAGE_URL_6 = PAGE_URL_WITH_NUMBER + 6;

    private final String PANEL_WITH_5_PAGENUMBER_WITHOUT_SCROLL_PAGE_1_ACTIVE = "<table class=\"paginationTable\">"  +
            "<tr class=\"paginationLine\"><td>" +
            "<a class=\"paginationLink active\" href=\"" + PAGE_URL_1 +  "\">1</a>" +
            "<a class=\"paginationLink\" href=\"" + PAGE_URL_2 +  "\">2</a>" +
            "<a class=\"paginationLink\" href=\"" + PAGE_URL_3 +  "\">3</a>" +
            "<a class=\"paginationLink\" href=\"" + PAGE_URL_4 +  "\">4</a>" +
            "<a class=\"paginationLink\" href=\"" + PAGE_URL_5 +  "\">5</a>" +
            "</td></tr>" +
            "</table>";

    private final String PANEL_WITH_5_PAGENUMBER_PAGE_2_ACTIVE = "<table class=\"paginationTable\">"  +
            "<tr class=\"paginationLine\"><td>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_1 +  "\"><</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_1 +  "\">1</a>" +
            "<a class=\"paginationLink active\" href=\"" + PAGE_URL_2 +  "\">2</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_3 +  "\">3</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_4 +  "\">4</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_5 +  "\">5</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_3 +  "\">></a>" +
            "</td></tr>" +
            "</table>";

    private final String PANEL_WITH_5_PAGENUMBER_PAGE_6_ACTIVE = "<table class=\"paginationTable\">"  +
            "<tr class=\"paginationLine\"><td>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_5 +  "\"><</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_2 +  "\">2</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_3 +  "\">3</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_4 +  "\">4</a>" +
            "<a class=\"paginationLink \" href=\"" + PAGE_URL_5 +  "\">5</a>" +
            "<a class=\"paginationLink active\" href=\"" + PAGE_URL_6 +  "\">6</a>" +
            "</td></tr>" +
            "</table>";


    private PaginationTagHandler paginationTagHandler;


    @Before
    public void setUp(){
        mockServletContext = new MockServletContext();
        mockPageContext = new MockPageContext(mockServletContext);

        paginationTagHandler = new PaginationTagHandler();
        paginationTagHandler.setPageContext(mockPageContext);
    }

    @Test
    public void shouldNotReturnPanelOnePage() throws Exception {
        paginationTagHandler.setCurrentPage(1);
        paginationTagHandler.setTotalPages(1);
        paginationTagHandler.doStartTag();
        String output = ((MockHttpServletResponse)mockPageContext.getResponse()).getContentAsString();
        assertTrue(output.isEmpty());
    }

    @Test
    public void shouldNotReturnPanelZeroPage() throws Exception {
        paginationTagHandler.setCurrentPage(0);
        paginationTagHandler.setTotalPages(0);
        paginationTagHandler.doStartTag();
        String output = ((MockHttpServletResponse)mockPageContext.getResponse()).getContentAsString();
        assertTrue(output.isEmpty());
    }

    @Ignore
    public void shouldReturnPanelWithMaxDisplayedPageNumbersWithoutScroll() throws Exception{
        paginationTagHandler.setMaxPages(5);
        paginationTagHandler.setCurrentPage(1);
        paginationTagHandler.setTotalPages(5);
        paginationTagHandler.setPageURL(PAGE_URL);
        paginationTagHandler.doStartTag();
        String output = ((MockHttpServletResponse)mockPageContext.getResponse()).getContentAsString();
        assertTrue(output.equals(PANEL_WITH_5_PAGENUMBER_WITHOUT_SCROLL_PAGE_1_ACTIVE));

    }

    @Test
    public void shouldReturnPanelWith5PageNumberWithRightScrollOnly() throws Exception{
        paginationTagHandler.setMaxPages(5);
        paginationTagHandler.setCurrentPage(2);
        paginationTagHandler.setTotalPages(6);
        paginationTagHandler.setPageURL(PAGE_URL);
        paginationTagHandler.doStartTag();
        String output = ((MockHttpServletResponse)mockPageContext.getResponse()).getContentAsString();
        assertTrue(output.equals(PANEL_WITH_5_PAGENUMBER_PAGE_2_ACTIVE));
    }

    @Test
    public void shouldReturnPanelWith5PageNumberWithLeftScrollOnly() throws Exception{
        paginationTagHandler.setMaxPages(5);
        paginationTagHandler.setCurrentPage(6);
        paginationTagHandler.setTotalPages(6);
        paginationTagHandler.setPageURL(PAGE_URL);
        paginationTagHandler.doStartTag();
        String output = ((MockHttpServletResponse)mockPageContext.getResponse()).getContentAsString();
        assertTrue(output.equals(PANEL_WITH_5_PAGENUMBER_PAGE_6_ACTIVE));
    }

}