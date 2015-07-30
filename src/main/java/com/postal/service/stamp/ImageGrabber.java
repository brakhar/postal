package com.postal.service.stamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by brakhar on 02.04.15.
 */

@Component
public class ImageGrabber {

    final static Logger logger = LoggerFactory.getLogger(ImageGrabber.class);

    public static byte[] grab(String imageUrl) {
        URL url = null;
        ByteArrayOutputStream outputStream = null;

        if(imageUrl == null || imageUrl.trim().length() == 0) return null;

        try {
            url = new URL(imageUrl);

            outputStream = new ByteArrayOutputStream();

            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = url.openStream();

            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }

        } catch (MalformedURLException e) {
            logger.error("Error downloading image content by URL " + url);
            return new byte[]{};
        } catch (IOException e) {
            logger.error("Error downloading image content by URL " + url);
            return new byte[]{};
        }

        return outputStream.toByteArray();
    }

}
