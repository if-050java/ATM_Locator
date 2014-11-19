package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by roman on 19.11.14.
 */
@Service(value = "userService")
public class UserService {
    @Resource
    UsersDAO usersDAO;

    public User getUserByName(String name){
        return usersDAO.getUserByName(name);
    }
    @Transactional
    public void editUser(User user){
        usersDAO.editUser(user);
    }
}
