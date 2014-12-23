package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.EmailCreator;
import com.ss.atmlocator.utils.GenString;
import com.ss.atmlocator.utils.SendMails;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by roman on 19.11.14.
 */
@Service
public class UserService {
    @Autowired
    IUsersDAO usersDAO;

    @Autowired
    ATMService atmService;

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

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    private static final String EMAIL_SUBJECT = "Change user credentials";
    private static final int GEN_PASSWORD_LENGTH = 6;
    private static final String DELETING = "Try to delete user ";
    private static final String FINDING = "Try to find user with login or email ";
    private static final String UPDATING = "Try to update user ";
    private static final String SENDING_EMAIL = "Try to send e-mail to ";

    public User getUserByName(String name) {
        try {
            logger.debug(FINDING + name);
            return usersDAO.getUserByName(name);
        }catch (NoResultException nre){
            logger.warn(nre.getMessage(), nre);
            throw nre;
        }
    }

    public List<String> getNames(String partial){
        return usersDAO.getNames(partial);
    }

    public User getUserById(int id) {
        try {
            logger.debug(FINDING+id);
            return usersDAO.getUserById(id);
        }catch (NoResultException nre){
            logger.warn(nre.getMessage(), nre);
            throw nre;
        }
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        usersDAO.createUser(user);
    }

    public void editUser(User user, boolean genPassword) throws MessagingException{
        try {
            if (genPassword) {
                user.setPassword(GenString.genString(GEN_PASSWORD_LENGTH));
            };
            User mergedUser = merge(user);
            if (user.getPassword() != null) {
                mergedUser.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            }
            logger.info(UPDATING+mergedUser.getId());
            usersDAO.updateUser(merge(mergedUser));
            logger.info(SENDING_EMAIL+mergedUser.getEmail());
            sendMails.sendMail(mergedUser.getEmail() , EMAIL_SUBJECT, emailCreator.toUser(mergedUser, user.getPassword()));
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage(), iae);
            throw new PersistenceException(iae);
        }catch (MessagingException me){
            logger.error(me.getMessage(), me);
            throw me;
        }catch (PersistenceException pe){
            logger.error(pe.getMessage(), pe);
            throw pe;
        }
    }

    public void updateAvatar(int user_id, String avatar){
        usersDAO.updateAvatar(user_id, avatar);
    }

    public boolean checkExistLoginName(User user){
        return usersDAO.checkExistLoginName(user);
    };

    public boolean checkExistEmail(User user){
        return usersDAO.checkExistEmail(user);
    };

    private User merge(User user) throws IllegalAccessException {
        User persistedUser = getUserById(user.getId());
        for (Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(user) != null ? field.get(user) : field.get(persistedUser);
            field.set(persistedUser, value);
        }
        return persistedUser;
    }

    public void deleteUser(int id) {
        try {
            logger.info(DELETING+id);
            usersDAO.deleteUser(id);
        }catch (NoResultException nre){
            logger.error(nre.getMessage(), nre);
            throw nre;
        }catch (PersistenceException pe){
            logger.error(pe.getMessage(), pe);
            throw pe;
        }
    }

    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public Set<AtmOffice> getFavorites(int userId){
        return usersDAO.getFavorites(userId);
    }

    public void addFavorite(int userId, int atmId){
        try {
            logger.info("Try to add to favorites ATM with id " + atmId);
            usersDAO.addFavorite(userId, atmId);
        } catch (PersistenceException pe){
            logger.error(pe.getMessage(), pe);
        }

    }

    public void deleteFavorite(int userId, int atmId){
        try {
            logger.info("Try to delete atm with id = " + atmId + "from favorites for user_id " + userId);
            usersDAO.deleteFavorite(userId, atmId);
        } catch (PersistenceException pe){
            logger.error(pe.getMessage(), pe);
        }
    }


}
