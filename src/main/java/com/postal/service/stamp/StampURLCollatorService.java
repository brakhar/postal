package com.postal.service.stamp;

import com.postal.model.stamp.HTMLStamp;
import com.postal.model.stamp.Stamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by brakhar on 31.03.15.
 */

@Component
public class StampURLCollatorService implements Collator {

    private String pageUrl;

    @Autowired
    private HTMLGrabber htmlGrabber;

    @Autowired
    private StampTransformer stampTransformer;

    private String bigImageHost;

    @Override
    public List<Stamp> process() {
        List<HTMLStamp> htmlStampList = htmlGrabber.grab(pageUrl, bigImageHost);
        if(htmlStampList != null){
            return stampTransformer.transform(htmlStampList);
        }
        return null;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public void setHtmlGrabber(HTMLGrabber htmlGrabber) {
        this.htmlGrabber = htmlGrabber;
    }

    public void setStampTransformer(StampTransformer stampTransformer) {
        this.stampTransformer = stampTransformer;
    }

    public void setBigImageHost(String bigImageHost) {
        this.bigImageHost = bigImageHost;
    }

    public String getBigImageHost() {
        return bigImageHost;
    }
}
