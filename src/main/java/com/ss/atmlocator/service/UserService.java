package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * Created by roman on 19.11.14.
 */
@Service
public class UserService {
    @Autowired
    IUsersDAO usersDAO;

    @Autowired
    ValidateUserLoginService loginValidator;

    @Autowired
    ValidateUserEmailService emailValidator;

    @Autowired
    ValidateUserPasswordService passwordValidator;

    public User getUserByName(String name) {
        return usersDAO.getUserByName(name);
    }

    public User getUserByEmail(String email) {
        return usersDAO.getUserByEmail(email);
    }

    public User getUserById(int id) {
        return usersDAO.getUserById(id);
    }

    public void editUser(User user) {
        User persistedUser = getUserById(user.getId());
        usersDAO.updateUser(merge(user, persistedUser));
    }

    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

    /* Verify existing of login in DB */
    public boolean checkExistLoginName(String login) {
        return usersDAO.checkExistLoginName(login);
    }

    ;

    /* Verify existing of email address in DB */
    public boolean checkExistEmail(String email) {
        return usersDAO.checkExistEmail(email);
    }

    ;

    public void checkUserProfile(User user, Errors errors){
        User persistedUser = getUserById(user.getId());
        //Checking login
        if(! user.getLogin().equals(persistedUser.getLogin())){
            loginValidator.validate(user.getLogin(),errors);
        };
        //Checking email
        if(! user.getEmail().equals(persistedUser.getEmail())){
            emailValidator.validate(user.getEmail(),errors);
        };
        //Checking password
        if(! user.getPassword().equals(persistedUser.getPassword())){
            passwordValidator.validate(user.getPassword(),errors);
        }
        //Checking enabled
        if(user.getEnabled() == persistedUser.getEnabled()){

        }
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
        mergedUser.setPassword(updatedUser.getPassword() == null ? persistedUser.getPassword() : updatedUser.getPassword());
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
    public boolean isModified(User updatedUser) {
        User persistedUser = getUserById(updatedUser.getId());
        if (
                updatedUser.getLogin().equals(persistedUser.getLogin()) &&  //login didn't change
                        updatedUser.getEmail().equals(persistedUser.getEmail()) &&  //email didn't change
                        updatedUser.getPassword().equals(persistedUser.getPassword()) &&  //password didn't change
                        updatedUser.getEnabled() == persistedUser.getEnabled()            //enabled didn't change
                ) {
            return false;
        } else {
            return true;
        }
    }


}
