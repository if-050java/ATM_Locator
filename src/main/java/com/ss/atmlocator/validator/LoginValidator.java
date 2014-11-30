package com.ss.atmlocator.validator;

/**
 * Created by roman on 11/28/2014.
 */
import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.UserCredMatcher;
import com.ss.atmlocator.utils.UtilEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;



/**
 *
 */
public class LoginValidator implements Validator {

    @Autowired
    @Qualifier("usercredmatcher")
    private UserCredMatcher userCredMatcher;

    @Autowired
    private IUsersDAO usersDAO;

    @Autowired
    private MessageSource messages;

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        final User user = (User)object;
        if(validateLogin(user.getLogin())){
            if(checkLogin(user)){
                errors.rejectValue(UtilEnums.UserResponseField.LOGIN.toString(),
                        messages.getMessage("login.exists", null, Locale.ENGLISH));
            }
        }
        else{
            errors.rejectValue(UtilEnums.UserResponseField.LOGIN.toString(),
                    messages.getMessage("invalid.login", null, Locale.ENGLISH));
        }
    }

    private boolean validateLogin(String email){
        return userCredMatcher.validateLogin(email);
    }

    private boolean checkLogin(User user){
        return usersDAO.checkExistLoginName(user);
    }


}
