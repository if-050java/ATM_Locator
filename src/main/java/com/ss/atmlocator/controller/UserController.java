package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by roman on 19.11.14.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/profile")
    public String profile(Model model, Principal principal) {

        String userName = principal.getName();
        model.addAttribute("user", userService.getUserByName(userName));

        return "profile";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(User user){
        System.out.println(user);
        userService.editUser(user);
        return "redirect:profile";
    }


}
