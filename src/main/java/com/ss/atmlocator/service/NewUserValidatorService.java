package com.ss.atmlocator.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.ss.atmlocator.entity.User;

/**
 *
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


    public void validate(User user, Errors errors){
        loginValidator.validate(user.getLogin(),errors);
        passwordValidator.validate(user.getPassword(),errors);
        emailValidator.validate(user.getEmail(),errors);
    }
}
