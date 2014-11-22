package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.ValidateUsersFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {

    //Клас для надсилання відповіді про успішність/неуспішність операції
    enum ResultResponse {
        ERROR,
        SUCCESS,
        CANT_REMOVE_YOURSELF,
        INVALID_LOGIN,
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    @Autowired
    UserService userService;

    @Autowired
    ValidateUsersFieldsService validationService;

    @Autowired
    @Qualifier("jdbcUserService")
    public UserDetailsManager userDetailsManager;

    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    public
    @ResponseBody
    User findUser(HttpServletRequest request) {
        String findBy = request.getParameter("findBy");
        String findValue = request.getParameter("findValue");
        User response;
        try {
            if (findBy.equals("name")) {
                response = userService.getUserByName(findValue);
            } else {
                response = userService.getUserByEmail(findValue);
            }
            return response;
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
    ResultResponse deleteUser(HttpServletRequest request) {

        //get ID of logged user before updating
        int loggedUserId = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        //get ID of user for deleting
        int id = Integer.parseInt(request.getParameter("id"));

        //User cant delete his own profile
        if(id== loggedUserId){
            return ResultResponse.CANT_REMOVE_YOURSELF;
        }

        try {
            userService.deleteUser(id);
            return ResultResponse.SUCCESS;
        } catch (PersistenceException pe) {
            return ResultResponse.ERROR;
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public  @ResponseBody
    EnumSet<ResultResponse> updateUser(HttpServletRequest request) {

        //Set of results.
        EnumSet<ResultResponse> results = EnumSet.noneOf(ResultResponse.class);

        //Creating updated user profile from form
        int id = Integer.parseInt(request.getParameter("id"));
        String newLogin = request.getParameter("login");
        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("password");
        int enabled = Integer.parseInt(request.getParameter("enabled"));
        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        //validating user profile
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), User.class.getName());
        validationService.validate(updatedUser, errors);

        if(! errors.hasErrors()) {
            //if validation was successful try to save
            try {
                //get ID of logged user before updating
                int loggedUserId = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
                userService.editUser(updatedUser);
                //if admin want to change own profile relogin this user with new name
                if (updatedUser.getId() == loggedUserId) {
                    doAutoLogin(updatedUser.getLogin());
                }
                results.add(ResultResponse.SUCCESS);
                return results;
            } catch (PersistenceException pe) {
                results.add(ResultResponse.ERROR);
                return results;
            }
        } else {
            //if validation unsuccessful add all errors to response
            for(ObjectError error: errors.getAllErrors()){
                if(error.getCodes()[2].equals("INVALID_LOGIN")) {
                    results.add(ResultResponse.INVALID_LOGIN);
                    continue;
                }
                if(error.getCodes()[2].equals("INVALID_EMAIL")) {
                    results.add(ResultResponse.INVALID_EMAIL);
                    continue;
                }
                if(error.getCodes()[2].equals("INVALID_PASSWORD")) {
                    results.add(ResultResponse.INVALID_PASSWORD);
                    continue;
                }
            }
            return results;
        }
    }

    private void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
