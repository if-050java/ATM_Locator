package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.NewUserValidatorService;
import com.ss.atmlocator.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.util.EnumSet;
import java.util.HashMap;

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
    NewUserValidatorService validationService;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public
    @ResponseBody
    User findUser(@RequestParam(FIND_BY) String findBy,
                  @RequestParam(FIND_VALUE) String findValue) {
        try {
            if (findBy.equals(USER_LOGIN)) {
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
    EnumSet deleteUser(HttpServletRequest request) {
        //id of user will be deleted
        int id = Integer.parseInt(request.getParameter(USER_ID));
        //id of user who want to delete
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
    public  @ResponseBody EnumSet
    updateUser(@RequestParam(USER_ID) int id,
               @RequestParam(USER_LOGIN) String newLogin,
               @RequestParam(USER_EMAIL) String newEmail,
               @RequestParam(USER_PASSWORD) String newPassword,
               @RequestParam(USER_ENABLED) int enabled) {

        //Creating user from request parameters
        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        //checking if nothing to update
        if (!userService.isModified(updatedUser)) {
            return EnumSet.of(UserControllersResponse.NOTHING_TO_UPDATE);
        }
        ;

        //validating user profile
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        userService.checkUserProfile(updatedUser, errors);
        if (errors.hasErrors()) {//if validation unsuccessful add all errors to response
            EnumSet<UserControllersResponse> response = EnumSet.noneOf(UserControllersResponse.class);
            for (ObjectError error : errors.getAllErrors()) {
                response.add(UserControllersResponse.valueOf(error.getCode()));
            }
            return response;
        }else {
            return EnumSet.of(UserControllersResponse.SUCCESS);
        }
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
