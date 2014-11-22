package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.dao.UsersDAO;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class SignUpController {

    private final int ENABLED_USER_STATUS = 1;

    private final String DEFAULT_USER_AVATAR = "defaultUserAvatar.jpg";

    @Autowired
    private IUsersDAO usersDAO;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }



    @RequestMapping(value = "/registering", method = RequestMethod.POST)
    public String registering(@RequestParam("inputLogin") String login,
                              @RequestParam("inputEmail") String email,
                              @RequestParam("inputPassword") String password,
                              @RequestParam(value = "signMe",required = false)  String signMe,
                              Model model) {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setEnabled(ENABLED_USER_STATUS);
        user.setAvatar(DEFAULT_USER_AVATAR);
        Role role = usersDAO.getDefaultUserRole();
        Set<Role> roles = new HashSet<Role>(0);
        roles.add(role);
        user.setRoles(roles);

        usersDAO.createUser(user);
        model.addAttribute("login",login);
        model.addAttribute("email",email);
        model.addAttribute("password",password);
        model.addAttribute("signMe",signMe);
        model.addAttribute("role",role);
        return "signup";
    }

}
