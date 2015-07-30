package com.postal.service.user;

import com.postal.model.user.User;
import com.postal.repository.UserRepository;
import com.postal.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by brakhar on 6/8/2015.
 */

@Component
public class UserService extends CRUDService<User, String> {

    @Autowired
    public UserService(UserRepository repository) {
        super(repository);
    }
}
