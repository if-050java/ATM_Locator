package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.ValidateUsersFieldsService;
import com.ss.atmlocator.utils.EmailCreator;
import com.ss.atmlocator.utils.SendMails;
import com.ss.atmlocator.utils.UserControllerResponse;
import com.ss.atmlocator.utils.UserControllerResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Locale;

/**
 * Class for processing requests from adminUsers page
 */

@Controller
public class AdminUsersController {

    //Request parameters for finding users
    private final String FIND_BY = "findBy";
    private final String FIND_VALUE = "findValue";
    //Request parameters for user fields
    private final String USER_ID = "id";
    private final String USER_LOGIN = "login";
    private final String USER_EMAIL = "email";
    private final String USER_PASSWORD = "password";
    private final String USER_ENABLED = "enabled";

    private final String EMAIL_SUBJECT = "ATM_Locator registration";

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    ValidateUsersFieldsService validationService;

    @Autowired
    @Qualifier("mail")
    private SendMails sendMails;

    @Autowired
    private MessageSource messages;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    @ResponseBody
    public User findUser(@RequestParam(FIND_BY) String findBy,
                         @RequestParam(FIND_VALUE) String findValue) {
        try {
            if (findBy.equals(USER_LOGIN)) {
                return userService.getUserByName(findValue);
            } else {
                return userService.getUserByEmail(findValue);
            }
        } catch (PersistenceException pe) {
            //if user not found
            User userNotFound = new User();
            userNotFound.setId(-1);
            return userNotFound;
        }
    }

    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public String adminUsers(ModelMap model) {
        model.addAttribute("active", "adminUsers");
        return "adminUsers";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public
    @ResponseBody
    UserControllerResponse deleteUser(@RequestParam(USER_ID) int id) {
        //id of user who want to delete
        int currentLoggedUserId =  userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        //Check user want to remove himself
        if (id == currentLoggedUserId) {
            return new UserControllerResponse(UserControllerResponseStatus.ERROR,
                                              messages.getMessage(UserControllerResponseStatus.CANT_REMOVE_YOURSELF.toString(),
                                              null, Locale.ENGLISH));
        };
        try {
            userService.deleteUser(id);

            return new UserControllerResponse(UserControllerResponseStatus.SUCCESS,
                                              messages.getMessage(UserControllerResponseStatus.SUCCESS.toString(),
                                              null, Locale.ENGLISH));
        } catch (PersistenceException pe) {
            return new UserControllerResponse(UserControllerResponseStatus.ERROR,
                                              messages.getMessage(UserControllerResponseStatus.ERROR.toString(),
                                              null, Locale.ENGLISH));
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public UserControllerResponse updateUser(@RequestParam(USER_ID) int id,
                                             @RequestParam(USER_LOGIN) String newLogin,
                                             @RequestParam(USER_EMAIL) String newEmail,
                                             @RequestParam(USER_PASSWORD) String newPassword,
                                             @RequestParam(USER_ENABLED) int enabled) {

        //Creating user from request parameters
        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        //checking if nothing to update
        if (!userService.isModified(updatedUser)) {
            return new UserControllerResponse(UserControllerResponseStatus.ERROR,
                                              messages.getMessage(UserControllerResponseStatus.NOTHING_TO_UPDATE.toString(),
                                              null, Locale.ENGLISH));
        }

        //validating user profile
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        validationService.validate(updatedUser, errors);
        if (errors.hasErrors()) {//if validation unsuccessful add all errors to response
            StringBuilder responseMessage=new StringBuilder();
            for (ObjectError error : errors.getAllErrors()) {
                responseMessage.append(error.getCode() + "; ");
            }
            return new UserControllerResponse(UserControllerResponseStatus.ERROR,responseMessage.toString());
        } else {
            userService.editUser(updatedUser);
            return new UserControllerResponse(UserControllerResponseStatus.SUCCESS,
                                              messages.getMessage(UserControllerResponseStatus.SUCCESS.toString(),
                                              null, Locale.ENGLISH));
        }
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public UserControllerResponse sendEmail(@RequestParam(USER_ID) int id) {
        User user = userService.getUserById(id);
        try {
            sendMails.sendMail(user.getEmail(), EMAIL_SUBJECT, EmailCreator.create(user));
            return new UserControllerResponse(UserControllerResponseStatus.SUCCESS,
                                              messages.getMessage(UserControllerResponseStatus.EMAIL_SUCCESS.toString(),
                                              null, Locale.ENGLISH));
        }catch (MailException me){
            return new UserControllerResponse(UserControllerResponseStatus.ERROR,
                                              messages.getMessage(UserControllerResponseStatus.EMAIL_ERROR.toString(),
                                              null, Locale.ENGLISH));
        }
    }

     /**
     *Autorelogin user after change own login(userName)
     *
     * @param username new name of loggined user
     */
    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
