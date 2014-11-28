package com.ss.atmlocator.validator;

/**
 * Created by us8610 on 11/28/2014.
 */
import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.User;
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
public class EmailValidator implements Validator {

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
        if(validateEmail(user.getEmail())){
            if(checkEmail(user)){
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

    private boolean checkEmail(User user){
        return usersDAO.checkExistEmail(user);
    }


}
