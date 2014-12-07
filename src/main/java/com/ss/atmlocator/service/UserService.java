package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.exception.NotValidException;
import com.ss.atmlocator.utils.Constants;
import com.ss.atmlocator.utils.EmailCreator;
import com.ss.atmlocator.utils.GenString;
import com.ss.atmlocator.utils.SendMails;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.validation.MapBindingResult;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import java.lang.reflect.Field;
import java.util.HashMap;

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
    @Qualifier("emailcreator")
    EmailCreator emailCreator;

    @Autowired
    @Qualifier("mail")
    private SendMails sendMails;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

    @Autowired
    UserProfileValidator validationService;

    private static final String EMAIL_SUBJECT = "Change user credentials";
    private static final int GEN_PASSWORD_LENGTH = 6;


    public User getUserByName(String name) {
        return usersDAO.getUserByName(name);
    }

    public User getUserById(int id) {
        return usersDAO.getUserById(id);
    }

    public void createUser(User user){
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
        usersDAO.createUser(user);
    }

    public void editUser(User user, boolean genPassword) throws NotValidException {
        try {
            //validating user profile
            MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
            validationService.validate(user, null, errors);
            if (errors.hasErrors()) {
                throw new NotValidException();
            };
            //generate password if required
            if(genPassword){
                user.setPassword(GenString.genString(GEN_PASSWORD_LENGTH));
            }
            sendMails.sendMail(getUserById(user.getId()).getEmail(), EMAIL_SUBJECT, emailCreator.toUser(user));
            if(user.getPassword() != null){
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(),null));
            }
            usersDAO.updateUser(merge(user));
        }catch (IllegalAccessException iae){
            throw new PersistenceException("Can't merge this user");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private User merge(User user) throws IllegalAccessException {
        User persistedUser = getUserById(user.getId());
        for(Field field : User.class.getDeclaredFields()){
            field.setAccessible(true);
            Object value = field.get(user) != null ? field.get(user) : field.get(persistedUser);
            field.set(persistedUser, value);
        }
        return persistedUser;
    }

    public boolean isNotModified(User user){
        if(user.getId() <=0){
            throw new IllegalArgumentException("User id can't be 0 or less");
        }
        try {
            User persistedUser = getUserById(user.getId());
            for (Field field : User.class.getDeclaredFields()) {
                field.setAccessible(true);
                if (! fieldEquals(field, user, persistedUser)) {
                    return false;
                }
            }
            return true;
        } catch (IllegalAccessException iae){
            throw new PersistenceException();
        }
    }

    private boolean fieldEquals(Field field, User newUser, User oldUser) throws IllegalAccessException {
            return field.get(newUser) == null || field.get(newUser).equals(field.get(oldUser));
    }

    public void deleteUser(int id) {
        usersDAO.deleteUser(id);
    }

    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


}
