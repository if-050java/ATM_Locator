package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.entity.UserStatus;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.Constants;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;

@Service
public class UserValidator implements Validator {

    private final static String ADMIN_ROLE_NAME = "ADMIN";
    private final static Role ADMIN_ROLE = new Role(ADMIN_ROLE_NAME);

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;


    /**
     * Rejects field errors into map <b>errors</b>
     * @param object validated image
     * @param errors map of field errors
     */
    public void validate(Object object, Errors errors) {
        User updatedUser = (User) object;

        boolean isAdmin = userService.getUser(updatedUser.getId()).getRoles().contains(ADMIN_ROLE);
        if (isAdmin && UserStatus.DISABLED == updatedUser.getEnabled()) {
            errors.rejectValue(Constants.USER_ENABLED, messages.getMessage("invalid.enabled", null, Locale.ENGLISH));
        }

        if (updatedUser.getLogin() != null) {
            validateLogin(updatedUser, errors);
        }
        if (updatedUser.getEmail() != null) {
            validateEmail(updatedUser, errors);
        }
        if (updatedUser.getPassword() != null) {
            validatePassword(updatedUser, errors);
        }
        if (updatedUser.getName() != null) {
            validateNickName(updatedUser, errors);
        }
    }
    /**
     * Uses for checking supported classes
     * @param Clazz validated object
     * @return true if class is supported
     */
    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    /**
     * Rejects email field errors into map <b>errors</b>
     * @param user validated user
     * @param errors map of field errors
     */
    public void validateEmail(User user, Errors errors) {
        final String email = user.getEmail();
        if (userCredMatcher.validateEmail(email)) {
            if (userService.checkExistEmail(user)) {
                errors.rejectValue(Constants.USER_EMAIL,
                        messages.getMessage("email.exists", null, Locale.ENGLISH));
            }
        } else {
            errors.rejectValue(Constants.USER_EMAIL,
                    messages.getMessage("invalid.email", null, Locale.ENGLISH));
        }
    }

    /**
     * Rejects  login field errors into map <b>errors</b>
     * @param user validated user
     * @param errors map of field errors
     */
    public void validateLogin(User user, Errors errors) {
        final String login = user.getLogin();
        if (userCredMatcher.validateLogin(login)) {
            if (userService.checkExistLoginName(user)) {
                errors.rejectValue(Constants.USER_LOGIN,
                        messages.getMessage("login.exists", null, Locale.ENGLISH));
            }
        } else {
            errors.rejectValue(Constants.USER_LOGIN,
                    messages.getMessage("invalid.login", null, Locale.ENGLISH));
        }
    }

    /**
     * Rejects password field errors into map <b>errors</b>
     * @param user validated user
     * @param errors map of field errors
     */
    public void validatePassword(User user, Errors errors) {
        final String password = user.getPassword();
        if (!userCredMatcher.validatePassword(password)) {
            errors.rejectValue(Constants.USER_PASSWORD,
                    messages.getMessage("invalid.password", null, Locale.ENGLISH));
        }
    }

    /**
     * Rejects nickname field errors into map <b>errors</b>
     * @param user validated user
     * @param errors map of field errors
     */
    public void validateNickName(User user, Errors errors) {
        final String nickName = user.getName();
        if (!userCredMatcher.validateNickName(nickName)) {
            errors.rejectValue(Constants.USER_NAME,
                    messages.getMessage("invalid.nickname", null, Locale.ENGLISH));
        }
    }
}
