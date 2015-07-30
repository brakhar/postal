package com.postal.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by brakhar on 15.05.15.
 */
public class PaginationTagHandler extends TagSupport{

    public static final String CSS_ACTIVE_NUMBER = "active";
    private int currentPage;
    private int totalPages;
    private String content;
    private int maxPages;
    private String pageURL;

    private String startTagTable = "<table class=\"paginationTable\">";
    private String endTagTable = "</table>";

    private String startTagRow = "<tr class=\"paginationLine\"><td>";
    private String endTagRow = "</td></tr>";

    private String linkTemplate = "<a class=\"paginationLink %s\" href=\"%s\">%s</a>";

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();


            out.print(generateContent());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    private String generateContent(){
        StringBuffer content = new StringBuffer();

        if (totalPages == 1 || totalPages == 0) return content.toString();

        addHeader(content);
        boolean lastPage = currentPage == totalPages;

        int pgStart = Math.max(currentPage - maxPages / 2, 1);

        int pgEnd = pgStart + maxPages;

        if (pgEnd > totalPages + 1) {
            int diff = pgEnd - totalPages;
            pgStart -= diff - 1;
            if (pgStart < 1)
                pgStart = 1;
            pgEnd = totalPages + 1;
        }

        if (currentPage > 1)
            content.append(constructLink(currentPage - 1, "<", null));

        for (int i = pgStart; i < pgEnd; i++) {
            if (i == currentPage)
                content.append(constructLink(currentPage, Integer.toString(currentPage), "activePage"));
            else
                content.append(constructLink(i));
        }

        if (!lastPage)
            content.append(constructLink(currentPage + 1, ">", null));

        addFotter(content);
        return content.toString();
    }


    private String constructLink(int page) {
        return constructLink(page, String.valueOf(page), null);
    }

    private String constructLink(int page, String text, String className) {
        String activeStyle = "";

        if (className != null) {
            activeStyle = CSS_ACTIVE_NUMBER;
        }
        return String.format(linkTemplate, activeStyle, pageURLGenerator(page), text);
    }

    private void addHeader(StringBuffer content){
        content.append(startTagTable);
        content.append(startTagRow);
    }

    private void addFotter(StringBuffer content){
        content.append(endTagRow);
        content.append(endTagTable);
    }

    private String pageURLGenerator(int number){
        return getPageURL() + "?pageNumber="+number;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public String getPageURL() {
        return pageURL;
    }
}
