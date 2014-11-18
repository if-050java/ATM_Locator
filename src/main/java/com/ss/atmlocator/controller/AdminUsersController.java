package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Vasyl Danylyuk on 14.11.2014.
 */

@Controller
public class AdminUsersController {
    @Autowired
    private IUsersDAO usersDAO;

    @RequestMapping("/saveUsersProfile")
    public String sendCredentials(@ModelAttribute("user") User user){
        System.out.println(user.getLogin());
        return "ok";
    }

    @RequestMapping(value = "/usersProfile", method = RequestMethod.GET)
    public String showProfile(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = usersDAO.getUserByName(userName);

        model.addAttribute(user);
        return "usersProfile";
    }
}
