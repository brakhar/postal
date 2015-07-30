package com.postal.service.stamp;

import com.postal.model.stamp.HTMLImage;
import com.postal.model.stamp.HTMLStamp;
import com.postal.model.stamp.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nazar_Kharchuk on 3/31/2015.
 */

@Component
public class HTMLGrabber {

    final static Logger logger = LoggerFactory.getLogger(HTMLStamp.class);

    @Autowired
    private ImageGrabber imageGrabber;

    @Autowired
    private ImageService imageService;

    private String imageHost;

    private String bigImageHost;

    public List<HTMLStamp> grab(String pageUrl, String bigImageHost) {

        imageHost = getImageURLHost(pageUrl);

        this.bigImageHost = bigImageHost;

        Document doc = getDocument(pageUrl);

        if (doc != null){
            return getHTMLStampList(doc.getElementsByTag("table"));
        }

        return Collections.EMPTY_LIST;
    }

    private String getImageURLHost(String pageUrl){
        String hostPortURL = null;
        try {
            URL url = new URL(pageUrl);
            hostPortURL = url.getProtocol() + "://" + url.getHost();
            if(url.getPort() != -1) {
                hostPortURL += ":" + url.getPort();
            }
            return hostPortURL;
        } catch (MalformedURLException e) {
            logger.error("Error getting host and port from siteURL", e);
            return null;
        }
    }

    private Document getDocument(String pageUrl){
        Document doc = null;
        HTMLStamp htmlStamp = null;
        try {
            doc = Jsoup.connect(pageUrl).get();
        } catch (IOException e) {
            logger.error("Error during getting connection with site " + pageUrl, e);
        }
        return doc;
    }



    private List<HTMLStamp> getHTMLStampList(Elements stampBlocks){
        List<HTMLStamp> htmlStampList = new ArrayList<HTMLStamp>();

        for (Element link : stampBlocks) {
            if(link.getElementsByTag("resources/img").size() != 0){
                for(Element imgLink : link.getElementsByTag("resources/img")){
                    Element imgElement = imgLink.getElementsByTag("resources/img").first();
                    if(imgElement.hasAttr("alt") && imgElement.attr("src").contains("/resources/img/s") && HTMLStamp.isSuitableStampForGrab(imgElement)){
                        htmlStampList.add(generate(imgElement));
                    }
                }
            }
        }
        return htmlStampList;
    }

    private HTMLStamp generate(Element imgElement){
        HTMLStamp htmlStamp = null;

        htmlStamp = new HTMLStamp(imgElement);

        persistImages(imgElement, htmlStamp);

        return htmlStamp;
    }

    private void persistImages(Element imgElement, HTMLStamp htmlStamp){
        HTMLImage htmlImage = getHTMLImage(imgElement);
        long imgId = imageService.insertImage(StampTransformer.transformHtmlImage(htmlImage));
        long bigImgId = imageService.insertImage(getBigImage(htmlImage.getSrc()));

        htmlStamp.setImgId(imgId);
        htmlStamp.setBigImgId(bigImgId);
    }

    private HTMLImage getHTMLImage(Element imgLink){

        HTMLImage htmlImage = new HTMLImage(imgLink);
        if(imageHost != null){
            htmlImage.setContent(imageGrabber.grab(imageHost + htmlImage.getSrc()));
        }

        return htmlImage;
    }

    private Image getBigImage(String imgSrc){
        return new Image(imageGrabber.grab(bigImageHost+createBigImgSrc(imgSrc)));
    }

    private String createBigImgSrc(String imgSrc){
        return imgSrc.replace("/resources/img/s/", "/b/");
    }

    public void setImageGrabber(ImageGrabber imageGrabber) {
        this.imageGrabber = imageGrabber;
    }

    public ImageGrabber getImageGrabber() {
        return imageGrabber;
    }
}
