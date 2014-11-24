package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.UserConformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by roman on 19.11.14.
 */
@Service
public class UserService {
    @Autowired
    IUsersDAO usersDAO;

    public User getUserByName(String name) {
        return usersDAO.getUserByName(name);
    }

    public User getUserByEmail(String email){
        return usersDAO.getUserByEmail(email);
    }

    public User getUserById(int id){
        return usersDAO.getUserById(id);
    }

    public void editUser(User user) {
        User persistedUser = getUserById(user.getId());
        usersDAO.updateUser(UserConformer.merge(user,persistedUser));
    }

    public void deleteUser(int id){
        usersDAO.deleteUser(id);
    }
    /* Verify existing of login in DB */
    public boolean checkExistLoginName(String login){
        return usersDAO.checkExistLoginName(login);
    };
    /* Verify existing of email address in DB */
    public boolean checkExistEmail(String email){
        return usersDAO.checkExistEmail(email);
    };
}
