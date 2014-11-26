package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by roman on 19.11.14.
 */
@Service
public class UserService {
    @Autowired
    IUsersDAO usersDAO;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

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

        persistedUser.setEnabled(user.getEnabled());
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
    /* Verify existing of login in DB */
    public boolean checkExistLoginName(String login){
        return usersDAO.checkExistLoginName(login);
    };
    /* Verify existing of email address in DB */
    public boolean checkExistEmail(String email){
        return usersDAO.checkExistEmail(email);
    };

    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
