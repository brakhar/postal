package com.postal.service.stamp;

import com.postal.model.stamp.Country;
import com.postal.repository.CountryRepository;
import com.postal.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 02.03.15.
 */

@Component
public class CountryService extends CRUDService<Country, Long> {

    @Autowired
    public CountryService(CountryRepository repository){
        super(repository);
    }

}
