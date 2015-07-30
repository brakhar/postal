package com.postal.repository;

import com.postal.model.logging.EventLogging;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 6/15/2015.
 */
public interface EventLoggingRepository extends JpaRepository<EventLogging, Long>{
}
