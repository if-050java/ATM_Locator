package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.utils.Constants;
import com.ss.atmlocator.utils.UserCredMatcher;
import com.ss.atmlocator.utils.UtilEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;


/**
 *
 */
public class ValidateUserLoginService implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    private IUsersDAO usersDAO;

    @Autowired
    private MessageSource messages;


    @Override
    public boolean supports(Class<?> Clazz) {
        return String.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        final String login = (String)object;
        if(validateLogin(login)){
            if(checkLogin(login)){
                errors.rejectValue(Constants.USER_LOGIN,
                        messages.getMessage("login.exists", null, Locale.ENGLISH));
            }
        }
        else{
            errors.rejectValue(Constants.USER_LOGIN,
                    messages.getMessage("invalid.login", null, Locale.ENGLISH));
        }
    }

    private boolean validateLogin(String login){
        return userCredMatcher.validateLogin(login);
    }

    private boolean checkLogin(String login){
        return usersDAO.checkExistLoginName(login);
    }


}
