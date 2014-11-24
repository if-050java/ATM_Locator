package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;

/**
 * Created by Vasyl Danylyuk on 24.11.2014.
 */
public class UserConformer {

    /**
     * Updating only noNull fields in old user profile
     *
     * @param updatedUser     User (with some null fields) for saving in db
     * @param persistedUser   User before updating(old profile)
     * @return                User with all NoNull fields
     */
    public static User merge(User updatedUser, User persistedUser){
        User mergedUser = new User();

        //Id
        mergedUser.setId(updatedUser.getId());
        //Enabled
        mergedUser.setEnabled(updatedUser.getEnabled());
        //Login
        mergedUser.setLogin( updatedUser.getLogin() == null ? persistedUser.getLogin() : updatedUser.getLogin() );
        //E-mail
        mergedUser.setEmail( updatedUser.getEmail() == null ? persistedUser.getEmail() : updatedUser.getEmail() );
        //Avatar
        mergedUser.setAvatar( updatedUser.getAvatar() == null ? persistedUser.getAvatar() : updatedUser.getAvatar() );
        //Password
        mergedUser.setPassword( updatedUser.getPassword() == null ? persistedUser.getPassword() : updatedUser.getPassword() );
        //Roles
        mergedUser.setRoles( updatedUser.getRoles() == null ? persistedUser.getRoles() : updatedUser.getRoles() );
        //Comments
        mergedUser.setAtmComments( updatedUser.getAtmComments() == null ? persistedUser.getAtmComments() : updatedUser.getAtmComments() );
        //Favorites
        mergedUser.setAtmFavorites( updatedUser.getAtmFavorites() == null ? persistedUser.getAtmFavorites() : updatedUser.getAtmFavorites() );

        return mergedUser;
    }

    public static boolean isCurrentLoggedUser(User user){

    }
}
