package com.ss.atmlocator.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import com.ss.atmlocator.entity.User;

import java.util.HashMap;

import static com.ss.atmlocator.utils.GenString.genString;

/**
 * Service class for validating new user credentials. If login or password is empty system will gen them.
 */

@Service
public class NewUserValidatorService {

    @Autowired
    @Qualifier("loginvalidator")
    private Validator loginValidator;

    @Autowired
    @Qualifier("passwordvalidator")
    private Validator passwordValidator;

    @Autowired
    @Qualifier("emailvalidator")
    private Validator emailValidator;

    private final int USER_PASSWORD_LENGTH = 6;

    public void validate(User user, Errors errors){
        String email = user.getEmail();
        String login = user.getLogin();
        String password = user.getPassword();
        /*Email validation*/
        emailValidator.validate(email,errors);
        if(errors.hasErrors()) return;
        /*Login validation*/
        if (login == null || login.length() ==0 ){

            MapBindingResult errorLocal;
            int index  = email.indexOf(64);

            login = email.substring(0,index);

            errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
            loginValidator.validate(login,errorLocal);

            if (errorLocal.hasErrors()){
                int i = 1;
                do{
                    login += i;
                    errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
                    loginValidator.validate(login,errors);
                    i++;
                }while (errorLocal.hasErrors()==true);
            }
            user.setLogin(login);
        }
        else{
            loginValidator.validate(login,errors);
        }
        /*Password validation*/
        if (password == null || password.length()==0){
            MapBindingResult errorLocal;
            do{
                password = genString(USER_PASSWORD_LENGTH);
                errorLocal = new MapBindingResult(new HashMap<String, String>(), this.getClass().getName());
                passwordValidator.validate(password,errors);
            } while (errorLocal.hasErrors()==true);
            user.setPassword(password);
        }
        else{
            passwordValidator.validate(user.getPassword(),errors);
        }
    }
}
