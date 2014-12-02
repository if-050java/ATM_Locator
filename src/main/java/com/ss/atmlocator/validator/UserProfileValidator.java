package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Service
public class UserProfileValidator {

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
    @Qualifier("imagevalidator")
    private Validator imageValidator;

    @Autowired
    private MessageSource messages;

    @Autowired
    private UserService userService;

    public void validate(User updatedUser, MultipartFile image, Errors errors) {
        User persistedUser = userService.getUserById(updatedUser.getId());

        if (!updatedUser.getLogin().equals(persistedUser.getLogin())) {
            loginValidator.validate(updatedUser.getLogin(), errors);
        }
        if (!updatedUser.getEmail().equals(persistedUser.getEmail())) {
            emailValidator.validate(updatedUser.getEmail(), errors);
        }
        if (updatedUser.getPassword().length()!=0) {
            passwordValidator.validate(updatedUser.getPassword(), errors);
        }
        if (updatedUser.getAvatar() != null && !updatedUser.getAvatar().equals(persistedUser.getAvatar())) {
            imageValidator.validate(image, errors);
        }
    }
}
