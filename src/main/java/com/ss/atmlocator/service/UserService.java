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

        if(user.getLogin() != null)
            persistedUser.setLogin(user.getLogin());
        if(user.getAvatar() != null)
            persistedUser.setAvatar(user.getAvatar());
        if(user.getEmail() != null)
            persistedUser.setEmail(user.getEmail());
        if(user.getPassword() != null)
            persistedUser.setPassword(user.getPassword());
        if(user.getRoles() != null)
            persistedUser.setRoles(user.getRoles());
        if(user.getAtmComments() != null)
            persistedUser.setAtmComments(user.getAtmComments());
        if(user.getAtmFavorites() != null)
            persistedUser.setAtmFavorites(user.getAtmFavorites());

        usersDAO.updateUser(persistedUser);
    }

    public void deleteUser(int id){
        usersDAO.deleteUser(id);
    }
}
