package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    private Md5PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

    public User getUserByName(String name) {
        return usersDAO.getUserByName(name);
    }

    public User getUserByEmail(String email) {
        return usersDAO.getUserByEmail(email);
    }

    public User getUserById(int id) {
        return usersDAO.getUserById(id);
    }

    public void createUser(User user){
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
        usersDAO.createUser(user);
    }

    public void editUser(User user) {
        User persistedUser = getUserById(user.getId());
        usersDAO.updateUser(merge(user, persistedUser));
    }

    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

    /**
     * Updating only noNull fields in old user profile
     *
     * @param updatedUser   User (with some null fields) for saving in db
     * @param persistedUser User before updating(old profile)
     * @return User with all NoNull fields
     */
    public User merge(User updatedUser, User persistedUser) {
        User mergedUser = new User();

        //Id
        mergedUser.setId(updatedUser.getId());
        //Enabled
        mergedUser.setEnabled(updatedUser.getEnabled());
        //Login
        mergedUser.setLogin(updatedUser.getLogin() == null ? persistedUser.getLogin() : updatedUser.getLogin());
        //E-mail
        mergedUser.setEmail(updatedUser.getEmail() == null ? persistedUser.getEmail() : updatedUser.getEmail());
        //Avatar
        mergedUser.setAvatar(updatedUser.getAvatar() == null ? persistedUser.getAvatar() : updatedUser.getAvatar());
        //Password
        mergedUser.setPassword(updatedUser.getPassword() != null ? passwordEncoder.encodePassword(updatedUser.getPassword(), null) : persistedUser.getPassword() );
        //Roles
        mergedUser.setRoles(updatedUser.getRoles() == null ? persistedUser.getRoles() : updatedUser.getRoles());
        //Comments
        mergedUser.setAtmComments(updatedUser.getAtmComments() == null ? persistedUser.getAtmComments() : updatedUser.getAtmComments());
        //Favorites
        mergedUser.setAtmFavorites(updatedUser.getAtmFavorites() == null ? persistedUser.getAtmFavorites() : updatedUser.getAtmFavorites());

        return mergedUser;
    }

    /**
     * @param updatedUser User  profile will be checked for modifying
     * @return true if one or more fields was changed
     */
    public boolean isNotModified(User updatedUser) {
        User persistedUser = getUserById(updatedUser.getId());
        return updatedUser.getLogin().equals(persistedUser.getLogin()) &&  //login didn't change
                updatedUser.getEmail().equals(persistedUser.getEmail()) &&  //email didn't change
                null == updatedUser.getPassword()  &&  //password didn't change
                updatedUser.getEnabled() == persistedUser.getEnabled() && //enabled didn't change
                updatedUser.getAvatar() == null;  // avatar didn't change
    }

    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /* Verify existing of login in DB */
    public boolean checkExistLoginName(String login) {
        return usersDAO.checkExistLoginName(login);
    }


    /* Verify existing of email address in DB */
    public boolean checkExistEmail(String email) {
        return usersDAO.checkExistEmail(email);
    }

}
