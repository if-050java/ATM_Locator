package com.ss.atmlocator.controller;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.utils.CheckUserCredCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;

@Controller

public class CheckUserCredAJAX {

    @Autowired
    @Qualifier("loginvalidator")
    private Validator loginValidator;

    @Autowired
    @Qualifier("emailvalidator")
    private Validator emailValidator;

    @Autowired
    private MessageSource messages;



    @RequestMapping(value = "/usercredlogin", method = RequestMethod.GET)
    public @ResponseBody CheckUserCredCode checkLogin(@RequestParam(value = "login") String login) {

        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());

        loginValidator.validate(login,errors);

        if(errors.hasErrors()){
            String errorCause = "";
            for(ObjectError err : errors.getAllErrors()){
                errorCause += err.getCode();
            }
            return new CheckUserCredCode(false,errorCause);
        }
        else{
            return new CheckUserCredCode(true,messages.getMessage("login.notexists",null, Locale.ENGLISH));
        }

    }

    @RequestMapping(value = "/usercredemail", method = RequestMethod.GET)
    public @ResponseBody CheckUserCredCode checkEmail(@RequestParam(value = "email") String email) {
        MapBindingResult errors = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());

        emailValidator.validate(email,errors);

        if(errors.hasErrors()){
            String errorCause = "";
            for(ObjectError err : errors.getAllErrors()){
                errorCause += err.getCode();
            }
            return new CheckUserCredCode(false,errorCause);
        }
        else{
            return new CheckUserCredCode(true,messages.getMessage("email.notexist",null, Locale.ENGLISH));
        }

    }

}
