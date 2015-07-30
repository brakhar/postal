package com.postal.service.parser;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by brakhar on 22.07.15.
 */

public abstract class AbstractStampParser {

    protected static Logger logger;

    protected Document getMainSiteDom(String url){
        URL siteURL = null;
        try {
            siteURL = new URL(url);
            HtmlCleaner cleaner = new HtmlCleaner();
            cleaner.getProperties().setAdvancedXmlEscape(true);

            TagNode tagNode = cleaner.clean(siteURL, "UTF-8");

            return new DomSerializer(
                    new CleanerProperties()).createDOM(tagNode);
        } catch (MalformedURLException e) {
            logger.error("Error getting DOM of main page!", e);
        } catch (ParserConfigurationException e) {
            logger.error("Error getting DOM of main page!", e);
        } catch (IOException e) {
            logger.error("Error getting DOM of main page!", e);
        }
        return null;
    }

}
