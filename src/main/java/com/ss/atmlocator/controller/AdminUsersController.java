package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.*;
import com.ss.atmlocator.validator.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Class for processing requests from adminUsers page
 */

@Controller
@RequestMapping("users")
public class AdminUsersController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    UserProfileValidator validationService;

    @Autowired
    @Qualifier("mail")
    private SendMails sendMails;

    @Autowired
    @Qualifier("emailcreator")
    EmailCreator emailCreator;

    @Autowired
    private MessageSource messages;


    public static final String EMAIL_SUBJECT = "Change user credentials";

    @RequestMapping(value = "/{value}", method = RequestMethod.GET)
    public ResponseEntity<User> findUser(@PathVariable("value")String value) {
        try {
            value = value.replace('*','.');
            return new ResponseEntity<User>(userService.getUserByName(value), HttpStatus.OK);
        } catch (PersistenceException pe) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String adminUsers(ModelMap model) {
        model.addAttribute("active", "adminUsers");
        return "adminUsers";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        //id of current logged user
        int currentLoggedUserId =  userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        //Check user want to remove himself
        if (id == currentLoggedUserId) {
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
        };
        try {
            userService.deleteUser(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (EntityNotFoundException enfe) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }catch (PersistenceException pe) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public OutResponse updateUser(@RequestParam(value=Constants.USER_ID) int id,
                                  @RequestParam(value=Constants.USER_LOGIN) String newLogin,
                                  @RequestParam(value=Constants.USER_EMAIL) String newEmail,
                                  @RequestParam(value=Constants.USER_PASSWORD, required = false) String newPassword,
                                  @RequestParam(value=Constants.USER_ENABLED) int enabled){

        //Creating user from request parameters
        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        //checking if nothing to update
        if (userService.isNotModified(updatedUser)) {
            return new OutResponse(Constants.INFO, new ErrorMessage(Constants.UPDATE,
                    messages.getMessage("user.nothing_to_update", null, Locale.ENGLISH)));
        }

        //validating user profile
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        validationService.validate(updatedUser, null, errors);

        if (errors.hasErrors()) {//if validation unsuccessful add all errors to response
            OutResponse response = new OutResponse(Constants.ERROR,null);
            for (FieldError error : errors.getFieldErrors()) {
                response.getErrorMessageList().add(new ErrorMessage(error.getField(), error.getCode()));
            }
            return response;
        };

        try {
             //try to update user in database
            userService.editUser(updatedUser);

            //try to send e-mail about changes to user
            //if password was changed send message with new password
            sendMails.sendMail(updatedUser.getEmail(), EMAIL_SUBJECT, emailCreator.create(Constants.UPDATE_TEMPLATE, updatedUser));

            //id of user who is logged
            int currentLoggedUserId =  userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
            //relogin if change yourself
            if (updatedUser.getId() == currentLoggedUserId) {
                userService.doAutoLogin(updatedUser.getLogin());
            };

            //Return SUCCESS
            return new OutResponse(Constants.SUCCESS, new ErrorMessage(Constants.UPDATE,
                    messages.getMessage("operation.success", null, Locale.ENGLISH)));
        }catch (PersistenceException pe){
            //Return PERSISTENCE_ERROR
            return new OutResponse(Constants.ERROR, new ErrorMessage(Constants.UPDATE,
                    messages.getMessage("operation.error", null, Locale.ENGLISH)));
        } catch(MailException me){
            //Return EMAIL_ERROR
            return new OutResponse(Constants.ERROR, new ErrorMessage(Constants.SEND_EMAIL,
                    messages.getMessage("email.error", null, Locale.ENGLISH)));
        }
    }
}
