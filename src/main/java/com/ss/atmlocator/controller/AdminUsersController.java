package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.ValidateUsersFieldsService;
import com.ss.atmlocator.utils.UserControllersResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.SplittableRandom;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {

    //Request parameters for finding users
    final String FIND_BY = "findBy";
    final String FIND_VALUE = "findValue";
    //Request parameters for user fields
    final String USER_ID = "id";
    final String USER_LOGIN = "login";
    final String USER_EMAIL = "email";
    final String USER_PASSWORD = "password";
    final String USER_ENABLED = "enabled";

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    ValidateUsersFieldsService validationService;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public
    @ResponseBody
    User findUser(HttpServletRequest request) {
        //Parameters for finding
        String findBy = request.getParameter(FIND_BY);
        String findValue = request.getParameter(FIND_VALUE);
        try {
            if (findBy.equals("name")) {
                return userService.getUserByName(findValue);
            } else {
                return userService.getUserByEmail(findValue);
            }
        } catch (PersistenceException pe) {
            return null;
        }
    }

    @RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
    public String adminUsers(ModelMap model) {
        model.addAttribute("active","adminUsers");
        return "adminUsers";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    EnumSet<UserControllersResponse> deleteUser(HttpServletRequest request) {
        //id of user will be deleted
        int id = Integer.parseInt(request.getParameter(USER_ID));
        int currentLoggedUserId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        //Check user want to remove himself
        if(id == currentLoggedUserId){
            return EnumSet.of(UserControllersResponse.CANT_REMOVE_YOURSELF); //User cant delete his own profile
        };

        try {
            userService.deleteUser(id);
            return EnumSet.of(UserControllersResponse.SUCCESS);
        } catch (PersistenceException pe) {
            return EnumSet.of(UserControllersResponse.ERROR);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public  @ResponseBody
    EnumSet<UserControllersResponse> updateUser(HttpServletRequest request) {

        //Creating updated user profile from form
        int id = Integer.parseInt(request.getParameter(USER_ID));
        String newLogin = request.getParameter(USER_LOGIN);
        String newEmail = request.getParameter(USER_EMAIL);
        String newPassword = request.getParameter(USER_PASSWORD);
        int enabled = Integer.parseInt(request.getParameter(USER_ENABLED));
        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        //checking if nothing to update
        if( ! userService.isModified(updatedUser)){
            return EnumSet.of(UserControllersResponse.NOTHING_TO_UPDATE);
        };

        //validating user profile
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        validationService.validate(updatedUser, errors);
        if(errors.hasErrors()) {//if validation unsuccessful add all errors to response
            EnumSet<UserControllersResponse> response = EnumSet.noneOf(UserControllersResponse.class);
            for(ObjectError error: errors.getAllErrors()){
                response.add(UserControllersResponse.valueOf(error.getCode()));
            }
            return response;
        };

            //if validation was successful try to save
            try {
                //Check existing login in database
                //don't check if it is this user and login didn't change
                if(! userService.getUserById(updatedUser.getId()).getLogin().equals(updatedUser.getLogin()))
                    if((userService.checkExistLoginName(updatedUser.getLogin()))) { //if login exist
                        results.add(UserControllersResponse.LOGIN_ALREADY_EXIST);
                        return results;
                    }
                //Check existing email in database
                //don't check if it is this user and e-mail didn't change
                if(! userService.getUserById(updatedUser.getId()).getEmail().equals(updatedUser.getEmail()))
                    if(userService.checkExistEmail(updatedUser.getEmail())) { //if not exist
                        results.add(UserControllersResponse.EMAIL_ALREADY_EXIST);
                        return results;
                    }
                //get ID of logged user before updating
                int loggedUserId = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
                userService.editUser(updatedUser);
                //if admin want to change own profile relogin this user with new name
                if (updatedUser.getId() == loggedUserId) {
                    userUtil.doAutoLogin(updatedUser.getLogin());
                }
                results.add(UserControllersResponse.SUCCESS);
                return results;
            } catch (PersistenceException pe) {
                results.add(UserControllersResponse.ERROR);
                return results;
            }
        } else {
            //if validation unsuccessful add all errors to response
            for(ObjectError error: errors.getAllErrors()){
                if(error.getCodes()[2].equals("INVALID_LOGIN")) {
                    results.add(UserControllersResponse.INVALID_LOGIN);
                    continue;
                }
                if(error.getCodes()[2].equals("INVALID_EMAIL")) {
                    results.add(UserControllersResponse.INVALID_EMAIL);
                    continue;
                }
                if(error.getCodes()[2].equals("INVALID_PASSWORD")) {
                    results.add(UserControllersResponse.INVALID_PASSWORD);
                    continue;
                }
            }
            return null;
        }

    /**
     * Autorelogin user after change own login(userName)
     *
     * @param username new name of loggined user
     */
    public void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
