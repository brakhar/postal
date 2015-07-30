package com.postal.model.stamp;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by brakhar on 31.03.15.
 */
public class HTMLImage {

    final static Logger logger = LoggerFactory.getLogger(HTMLImage.class);

    private String src;
    private String alt;
    private int height;
    private int width;
    private byte[] content;

    public HTMLImage() {}

    public HTMLImage(Element imgElement) {
        alt = imgElement.attr("alt");
        src = imgElement.attr("src");
        height = parseInt(imgElement.attr("height"));
        width = parseInt(imgElement.attr("width"));
    }

    private static int parseInt(String strValue){
        if (strValue != null && strValue.length() > 0){
            return Integer.parseInt(strValue);
        } else {
            return 0;
        }
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSrc() {
        return src;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return alt;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
