package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

import static com.ss.atmlocator.service.ValidateUserCredCode.ValidationKey;


/**
 *
 */
public class ValidateUserEmailService implements Validator {

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
        final String email = (String)object;
        if(validateEmail(email)){
            if(checkEmail(email)){
                errors.rejectValue(ValidationKey.EMAIL.toString(),
                        messages.getMessage("email.exists", null, Locale.ENGLISH));
            }
        }
        else{
            errors.rejectValue(ValidationKey.EMAIL.toString(),
                    messages.getMessage("invalid.email", null, Locale.ENGLISH));
        }
    }

    private boolean validateEmail(String email){
        return userCredMatcher.validateEmail(email);
    }

    private boolean checkEmail(String email){
        return usersDAO.checkExistEmail(email);
    }


}
