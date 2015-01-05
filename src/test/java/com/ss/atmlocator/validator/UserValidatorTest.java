package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;

/**
 * Created by roman on 05.01.15.
 */

public class UserValidatorTest {
    @Test
    public void testValidation() {
        User user = new User(1, "test", "test@gmail.com",null,null);
        UserValidator userValidator = new UserValidator();

        Errors errors = new BeanPropertyBindingResult(user,"user");
        userValidator.validate(user, errors);

        assertFalse(errors.hasErrors());
    }
}
