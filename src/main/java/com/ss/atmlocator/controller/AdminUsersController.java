package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {

    //Клас для надсилання відповіді про успішність/неуспішність операції
    enum ResultResponse {
        ERROR,
        SUCCESS,
        CANT_REMOVE_YOURSELF
    }

    @Autowired
    UserService userService;

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
    ResultResponse updateUser(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        String newLogin = request.getParameter("login");
        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("password");
        int enabled = Integer.parseInt(request.getParameter("enabled"));

        User updatedUser = new User(id, newLogin, newEmail, newPassword, enabled);

        try {
            //get ID of loginned user before updating
            int loginnedUserId = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
            userService.editUser(updatedUser);
            //if admin want to change own profile relogin this user with new name
            if(updatedUser.getId()== loginnedUserId){
                doAutoLogin(updatedUser.getLogin());
            }
            return ResultResponse.SUCCESS;
        } catch (PersistenceException pe) {
            return ResultResponse.ERROR;
        }
    }

    private void doAutoLogin(String username) {
        UserDetails user = userDetailsManager.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
