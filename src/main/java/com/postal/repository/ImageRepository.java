package com.postal.repository;

import com.postal.model.stamp.Image;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 12.05.15.
 */
public interface ImageRepository extends JpaRepository<Image, Long> {



}
