package com.postal.service.logging;

import com.postal.model.logging.EventLogging;
import com.postal.model.user.User;
import com.postal.model.user.UserStamp;
import com.postal.repository.EventLoggingRepository;
import com.postal.service.CRUDService;
import com.postal.service.stamp.StampService;
import com.postal.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 6/15/2015.
 */

@Component
public class EventLoggingService extends CRUDService<EventLogging, Long> {

    private final static Logger logger = LoggerFactory.getLogger(EventLoggingService.class);

    @Autowired
    private StampService stampService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserService userService;

    public EventLoggingService(){}

    @Autowired
    private EventLoggingService(EventLoggingRepository repository) {
        super(repository);
    }

    private User getUser(){
        return userService.getEntityById(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void logEvent(int eventTypeId, UserStamp userStamp) {
        getRepository().save(generateEventLogging(userStamp,eventTypeId));
    }

    private EventLogging generateEventLogging(UserStamp userStamp, Integer eventTypeId){
        EventLogging eventLogging = new EventLogging();
        eventLogging.setUser(userStamp.getUser());
        eventLogging.setEventType(eventTypeService.getEntityById(eventTypeId));
        eventLogging.setQuantity(userStamp.getFullListQuantity());
        eventLogging.setStamp(userStamp.getStamp());
        return eventLogging;
    }

}