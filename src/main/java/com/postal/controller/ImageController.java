package com.postal.controller;

import com.postal.model.stamp.Image;
import com.postal.service.stamp.ImageService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by brakhar on 20.03.15.
 */

@Controller
public class ImageController {

    final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;


    @RequestMapping(value = "/image/uploadImage.do", method = RequestMethod.POST)
    private @ResponseBody String uploadImage(MultipartHttpServletRequest request){
        Long imageId = null;
        JSONObject uploadResponse = new JSONObject();

        Iterator<String> itrator = request.getFileNames();
        MultipartFile multiFile = request.getFile(itrator.next());
        try {
            if (multiFile != null){
                byte[] imageContent = new byte[0];
                    imageContent = multiFile.getBytes();
                    imageId = imageService.insertImage(new Image(imageContent));
                    uploadResponse.put("imageId", imageId.toString());
            }
        } catch (IOException ioe) {
            logger.error("Error during saving image.", ioe);
        } catch (JSONException jsone) {
            logger.error("Error during create JSON response for image with id="+imageId, jsone);
        }
        return uploadResponse.toString();
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    private void showImage(@PathVariable Long id, HttpServletResponse response){
        byte[] imageContent = imageService.getImageById(id);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        try {
            response.getOutputStream().write(imageContent);
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.error("Error getting image by Id.", e);
        }

    }
}
