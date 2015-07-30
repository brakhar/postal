package com.postal.service.stamp;

import com.postal.model.stamp.Category;
import com.postal.repository.CategoryRepository;
import com.postal.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 02.03.15.
 */

@Component
public class CategoryService extends CRUDService<Category, Long> {

    @Autowired
    public CategoryService(CategoryRepository repository){
        super(repository);
    }

}
