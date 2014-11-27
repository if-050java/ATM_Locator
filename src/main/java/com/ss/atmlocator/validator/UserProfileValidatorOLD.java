package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class UserProfileValidatorOLD implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User newUser = ((User) object);

        User oldUser = userService.getUserById(newUser.getId());
        if (checkChanges(newUser, oldUser)) {
            errors.rejectValue("nothing", ValidationMessages.NOTHING_TO_UPDATE);
            return;
        }
        if (!validateLogin(newUser.getLogin())) {
            errors.rejectValue("login", ValidationMessages.INVALID_LOGIN);
        }
        if (!validateEmail(newUser.getEmail())) {
            errors.rejectValue("email", ValidationMessages.INVALID_EMAIL);
        }
        if (!newUser.getLogin().equals(oldUser.getLogin()) && userService.checkExistLoginName(newUser.getLogin())) {
            errors.rejectValue("login", ValidationMessages.LOGIN_ALREADY_EXIST);
        }
        if (!newUser.getEmail().equals(oldUser.getEmail()) && userService.checkExistEmail(newUser.getEmail())) {
            errors.rejectValue("email", ValidationMessages.EMAIL_ALREADY_EXIST);
        }
        if (!validatePassword(newUser.getPassword())) {
            errors.rejectValue("password", ValidationMessages.INVALID_PASSWORD);
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
