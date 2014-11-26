package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.ValidateUserCredCode;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Service
public class UserProfileValidator2 {

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
    private Validator imageValidator;

    @Autowired
    private MessageSource messages;

    @Autowired
    UserService userService;

    public void validate(User newUser,MultipartFile image, Errors errors) {

        User oldUser = userService.getUserById(newUser.getId());
        if (checkChanges(newUser, oldUser)) {
            errors.rejectValue(ValidateUserCredCode.ValidationKey.NOTHING.toString(),
                    messages.getMessage("nothing", null, Locale.ENGLISH));
        } else {
            loginValidator.validate(newUser.getLogin(), errors);
            passwordValidator.validate(newUser.getPassword(), errors);
            emailValidator.validate(newUser.getEmail(), errors);
            imageValidator.validate(image,errors);
        }
    }

    private boolean checkChanges(User newUser, User oldUser) {
        return newUser.getLogin().equals(oldUser.getLogin()) &&
                newUser.getEmail().equals(oldUser.getEmail()) &&
                newUser.getPassword().equals(oldUser.getPassword()) &&
                newUser.getAvatar() == null;
    }
}
