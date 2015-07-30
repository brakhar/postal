package com.postal.service.logging;

import com.postal.model.logging.EventType;
import com.postal.repository.EventTypeRepository;
import com.postal.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 6/15/2015.
 */

@Component
public class EventTypeService extends CRUDService<EventType, Integer> {

    @Autowired
    public EventTypeService(EventTypeRepository repository) {
        super(repository);
    }
}
