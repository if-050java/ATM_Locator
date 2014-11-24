package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.utils.CheckUserCredCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

public class CheckUserCred {

    @Autowired
    private IUsersDAO usersDAO;

    @RequestMapping(value = "/usercredlogin", method = RequestMethod.GET)
    public @ResponseBody CheckUserCredCode checkLogin(@RequestParam(value = "login") String login) {
        if (usersDAO.checkExistLoginName(login)){
            return new CheckUserCredCode(true,"Login already registered");
        }
        else return new CheckUserCredCode(false,"This login not registered");

    }

    @RequestMapping(value = "/usercredemail", method = RequestMethod.GET)
    public @ResponseBody CheckUserCredCode checkEmail(@RequestParam(value = "email") String email) {
        if (usersDAO.checkExistEmail(email)){
            return new CheckUserCredCode(true,"Email already registered");
        }
        else return new CheckUserCredCode(false,"This email not registered");

    }

}
