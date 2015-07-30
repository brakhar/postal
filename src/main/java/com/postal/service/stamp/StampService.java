package com.postal.service.stamp;

import com.postal.model.stamp.Stamp;
import com.postal.repository.StampRepository;
import com.postal.service.CRUDService;
import com.postal.service.logging.EventLoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by brakhar on 09.03.15.
 */


@Component
public class StampService extends CRUDService<Stamp, Long> {

    @Autowired
    private EventLoggingService eventLoggingRepositoryService;

    @Autowired
    public StampService(StampRepository repository){
        super(repository);
    }

    @Transactional
    public Page<Stamp> findBySearchValue(String searchValue, Integer year, Pageable pageable){
        return ((StampRepository)getRepository()).findBySearchValue(searchValue,year, pageable);
    }

    @Transactional
    public boolean checkIsStampIdPresentInDB(Long stampId) {
        Integer count = ((StampRepository)getRepository()).countByOriginalStampId(stampId);
        return count > 0;
    }

    @Transactional(readOnly = true)
    public Stamp getByCatalogNumber(String catalogNumber) {
        return ((StampRepository)getRepository()).getByCatalogNumber(catalogNumber);
    }

    @Transactional(readOnly = true)
    public Page<Stamp> findByUserToBuyStamps(Pageable pageable, String name) {
        return((StampRepository)getRepository()).findByUserToBuyStamps(pageable, name);
    }
}
