package com.postal.repository;

import com.postal.model.stamp.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 12.05.15.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
