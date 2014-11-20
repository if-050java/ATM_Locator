package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by roman on 19.11.14.
 */
public class UserService {
    @Autowired
    IUsersDAO usersDAO;

    public User getUserByName(String name) {
        return usersDAO.getUserByName(name);
    }

    public void editUser(User user) {
        usersDAO.updateUser(user);
    }
}
