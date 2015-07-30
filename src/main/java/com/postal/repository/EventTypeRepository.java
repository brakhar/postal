package com.postal.repository;

import com.postal.model.logging.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 6/15/2015.
 */
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
