package com.postal.repository;

import com.postal.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brakhar on 03.06.15.
 */
public interface UserRepository extends JpaRepository<User, String>{

    public User findByUserName(String userName);
}
