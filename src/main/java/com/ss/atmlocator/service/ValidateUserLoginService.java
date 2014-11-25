package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import static com.ss.atmlocator.service.ValidateUserCredCode.ValidationKey;
import static com.ss.atmlocator.service.ValidateUserCredCode.ValidationResult;

/**
 *
 */
public class ValidateUserLoginService implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    private IUsersDAO usersDAO;


    @Override
    public boolean supports(Class<?> Clazz) {
        return String.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        final String login = (String)object;
        if(validateLogin(login)){
            if(checkLogin(login)){
                errors.rejectValue(ValidationKey.LOGIN.toString(),
                    ValidationResult.LOGIN_ALREADY_REGISTERED.toString());
            }
        }
        else{
            errors.rejectValue(ValidationKey.LOGIN.toString(),
                    ValidationResult.INVALID_LOGIN.toString());
        }
    }

    private boolean validateLogin(String login){
        return userCredMatcher.validateLogin(login);
    }

    private boolean checkLogin(String login){
        return usersDAO.checkExistLoginName(login);
    }


}
