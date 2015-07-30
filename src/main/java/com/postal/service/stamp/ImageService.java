package com.postal.service.stamp;

import com.postal.model.stamp.Image;
import com.postal.exception.PostalRepositoryException;
import com.postal.repository.ImageRepository;
import com.postal.service.CRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by brakhar on 19.03.15.
 */

@Component("imageService")
public class ImageService {

    final static Logger logger = LoggerFactory.getLogger(CRUDService.class);

    @Autowired
    private ImageRepository repository;

    @Transactional(readOnly = true)
    public byte[] getImageById(Long imageId) {
            if (repository.getOne(imageId) != null){
                return repository.getOne(imageId).getContent();
            }
        return new byte[0];
    }

    @Transactional
    public void deleteImageById(Long imageId) throws PostalRepositoryException {
        try {
            repository.delete(imageId);
        }catch (Exception ex){
            throw new PostalRepositoryException(ex.getMessage());
        }
    }


    @Transactional()
    public Long insertImage(Image image){
        Image savedImage = null;
        savedImage = repository.save(image);

        return savedImage == null ? null : savedImage.getId();
    }

}
