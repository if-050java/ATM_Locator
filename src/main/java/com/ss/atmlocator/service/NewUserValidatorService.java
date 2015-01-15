package com.ss.atmlocator.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import com.ss.atmlocator.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.ss.atmlocator.utils.GenString.genString;

/**
 * Service class for validating new user credentials. If login or password is empty system will gen them.
 */

@Service
public class NewUserValidatorService {

    private final int AMPERSAND_CODE = 64;
    private final int USER_PASSWORD_LENGTH = 6;
    private DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

    @Autowired
    @Qualifier("loginvalidator")
    private Validator loginValidator;

    @Autowired
    @Qualifier("passwordvalidator")
    private Validator passwordValidator;

    @Autowired
    @Qualifier("emailvalidator")
    private Validator emailValidator;



    public void validate(User user, Errors errors){
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        /*Email validation*/
        emailValidator.validate(email,errors);
        if(errors.hasErrors()) return;
        /*Login validation*/
        if (login.isEmpty()){

            int index  = email.indexOf(AMPERSAND_CODE);
            login = email.substring(0,index);

            MapBindingResult errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
            loginValidator.validate(login,errorLocal);

            String tempLogin = login;

            if (errorLocal.hasErrors()){
                do{
                    tempLogin=login + dateFormat.format(new Date()) ;
                    errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
                    loginValidator.validate(tempLogin,errorLocal);
                }while (errorLocal.hasErrors()==true);
            }
            user.setLogin(tempLogin);
        }
        else{
            loginValidator.validate(login,errors);
        }
        /*Password validation*/
        if (password.isEmpty()){
            MapBindingResult errorLocal;
            do{
                password = genString(USER_PASSWORD_LENGTH);
                errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
                passwordValidator.validate(password,errorLocal);
            } while (errorLocal.hasErrors()==true);
            user.setPassword(password);
        }
        else{
            passwordValidator.validate(user.getPassword(),errors);
        }
    }
}
