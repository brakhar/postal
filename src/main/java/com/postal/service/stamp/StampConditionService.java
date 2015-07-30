package com.postal.service.stamp;

import com.postal.model.stamp.StampCondition;
import com.postal.repository.StampConditionRepository;
import com.postal.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 25.03.15.
 */

@Component
public class StampConditionService extends CRUDService<StampCondition, Long> {

    @Autowired
    public StampConditionService(StampConditionRepository repository) {
        super(repository);
    }
}
