package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *  Service class for validating users cred with existing verification on server
 */
@Service
public class ValidateUsersFieldsService implements Validator {


    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    private MessageSource messages;

    @Autowired
    private IUsersDAO usersDAO;

    @Autowired
    ValidateUserLoginService loginValidator;

    @Autowired
    ValidateUserEmailService emailValidator;

    @Autowired
    ValidateUserPasswordService passwordValidator;

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors){

        User newUser = (User)object;
        User persistedUser = usersDAO.getUserById(newUser.getId());
        //Checking login
        if(! newUser.getLogin().equals(persistedUser.getLogin())){
            loginValidator.validate(newUser.getLogin(),errors);
        };
        //Checking email
        if(! newUser.getEmail().equals(persistedUser.getEmail())){
            emailValidator.validate(newUser.getEmail(),errors);
        };
        //Checking password
        if(! newUser.getPassword().equals(persistedUser.getPassword())){
            passwordValidator.validate(newUser.getPassword(),errors);
        }
    }
}
