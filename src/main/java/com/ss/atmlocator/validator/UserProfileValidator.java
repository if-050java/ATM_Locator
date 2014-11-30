package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.service.ValidateUserCredCode;
import com.ss.atmlocator.utils.UserCredMatcher;
import com.ss.atmlocator.utils.UtilEnums;
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
    @Qualifier("loginValidator")
    private Validator loginValidator;

    @Autowired
    @Qualifier("passwordvalidator")
    private Validator passwordValidator;

    @Autowired
    @Qualifier("emailValidator")
    private Validator emailValidator;

    @Autowired
    @Qualifier("imagevalidator")
    private Validator imageValidator;

    @Autowired
    private MessageSource messages;

    @Autowired
    private UserService userService;

    public void validate(User newUser, MultipartFile image, Errors errors) {

        User oldUser = userService.getUserById(newUser.getId());

        if (checkChanges(newUser, oldUser)) {
            errors.rejectValue(UtilEnums.UserResponseStatus.NOTHING.toString(),
                    messages.getMessage("user.nothing_to_update", null, Locale.ENGLISH));
        } else {
                loginValidator.validate(newUser,errors);
                emailValidator.validate(newUser,errors);
                passwordValidator.validate(newUser.getPassword(),errors);
                imageValidator.validate(image,errors);

        }
    }

    private boolean checkChanges(User newUser, User oldUser) {
        return newUser.getLogin().equals(oldUser.getLogin()) &&
                newUser.getEmail().equals(oldUser.getEmail()) &&
                newUser.getPassword().equals(oldUser.getPassword()) &&
                newUser.getEnabled() == oldUser.getEnabled() &&
                newUser.getAvatar() == null;
    }
}
