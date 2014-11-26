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

import java.util.Locale;

import static com.ss.atmlocator.service.ValidateUserCredCode.ValidationKey;
import static com.ss.atmlocator.service.ValidateUserCredCode.ValidationResult;

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

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

        final String login = ((User)object).getLogin();
        final String email = ((User)object).getEmail();
        final String password = ((User)object).getPassword();
        /* Login validation */
        if(validateLogin(login)){
            /*if(checkLogin(login)){
                errors.rejectValue(ValidationKey.LOGIN.toString(),
                    ValidationResult.LOGIN_ALREADY_REGISTERED.toString());
            }*/
        }
        else{
            errors.rejectValue(ValidationKey.LOGIN.toString(),
                    messages.getMessage("invalid.login",null, Locale.ENGLISH));
        }
        /* Email validation*/
        if(validateEmail(email)){
           /* if(checkEmail(email)){
                errors.rejectValue(ValidationKey.EMAIL.toString(),
                        ValidationResult.EMAIL_ALREADY_REGISTERED.toString());
            }*/
        }
        else{
            errors.rejectValue(ValidationKey.EMAIL.toString(),
                    messages.getMessage("invalid.email", null, null));
        }
        /* Password validation */
        if(!validatePassword(password)){
            errors.rejectValue(ValidationKey.PASSWORD.toString(),
                    messages.getMessage("invalid.password", null, null));
        }
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

    private boolean checkEmail(String email){
        return usersDAO.checkExistEmail(email);
    }

    private boolean checkLogin(String login){
        return usersDAO.checkExistLoginName(login);
    }
}
