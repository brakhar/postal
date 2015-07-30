package com.postal.repository;

import com.postal.model.stamp.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 12.05.15.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
}
