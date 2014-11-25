package com.ss.atmlocator.service;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserValidatorService implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    UserService userService;

    enum ValidationResult {
        INVALID_LOGIN,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        LOGIN_ALREADY_EXIST,
        EMAIL_ALREADY_EXIST,
        NOTHING_TO_UPDATE
    }

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User newUser = ((User) object);

        User oldUser = userService.getUserById(newUser.getId());
        if (checkChanges(newUser, oldUser)) {
            errors.rejectValue("nothing", ValidationResult.NOTHING_TO_UPDATE.toString());
            return;
        }
        if (!validateLogin(newUser.getLogin())) {
            errors.rejectValue("login", ValidationResult.INVALID_LOGIN.toString());
        }
        if (!validateEmail(newUser.getEmail())) {
            errors.rejectValue("email", ValidationResult.INVALID_EMAIL.toString());
        }
        if (!newUser.getLogin().equals(oldUser.getLogin()) && userService.checkExistLoginName(newUser.getLogin())) {
            errors.rejectValue("login", ValidationResult.LOGIN_ALREADY_EXIST.toString());
        }
        if (!newUser.getEmail().equals(oldUser.getEmail()) && userService.checkExistEmail(newUser.getEmail())) {
            errors.rejectValue("email", ValidationResult.EMAIL_ALREADY_EXIST.toString());
        }
        if (!validatePassword(newUser.getPassword())) {
            errors.rejectValue("password", ValidationResult.INVALID_PASSWORD.toString());
        }
    }

    private boolean checkChanges(User newUser, User oldUser) {
        return newUser.getLogin().equals(oldUser.getLogin()) &&
               newUser.getEmail().equals(oldUser.getEmail()) &&
               newUser.getPassword().equals(oldUser.getPassword()) &&
               newUser.getAvatar() == null;
    }

    private boolean validateEmail(String email) {
        return userCredMatcher.validateEmail(email);
    }

    private boolean validateLogin(String login) {
        return userCredMatcher.validateLogin(login);
    }

    private boolean validatePassword(String password) {
        return userCredMatcher.validatePassword(password);
    }
}
