package com.ss.atmlocator.service;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ValidateUsersFieldsService implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    enum ValidationResult{
        INVALID_LOGIN,
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

        if(! validateLogin(((User)object).getLogin())){
            errors.rejectValue("login", ValidationResult.INVALID_LOGIN.toString());
        };

        if(! validateEmail(((User)object).getEmail())){
            errors.rejectValue("email", ValidationResult.INVALID_EMAIL.toString());
        };

        if(! validatePassword(((User)object).getPassword())){
            errors.rejectValue("password", ValidationResult.INVALID_PASSWORD.toString());
        };
    }


    private boolean validateEmail(String email){
       return userCredMatcher.validateEmail(email);
    }
    private boolean validateLogin(String login){
       return userCredMatcher.validateLogin(login);
    }
    private boolean validatePassword(String password){
        return userCredMatcher.validatePassword(password);
    }
}
