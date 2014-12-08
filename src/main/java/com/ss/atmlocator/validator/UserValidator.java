package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Service
public class UserValidator implements Validator {

    @Autowired
    @Qualifier("loginvalidator")
    private Validator loginValidator;

    @Autowired
    @Qualifier("passwordvalidator")
    private Validator passwordValidator;

    @Autowired
    @Qualifier("emailvalidator")
    private Validator emailValidator;
    @Autowired
    private MessageSource messages;

    public void validate(Object object, Errors errors) {
        User updatedUser = (User) object;

        if (updatedUser.getLogin() != null) {
            loginValidator.validate(updatedUser.getLogin(), errors);
        }
        if (updatedUser.getEmail() != null) {
            emailValidator.validate(updatedUser.getEmail(), errors);
        }
        if (updatedUser.getPassword() != null) {
            passwordValidator.validate(updatedUser.getPassword(), errors);
        }
    }

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }
}
