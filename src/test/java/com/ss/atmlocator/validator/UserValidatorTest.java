package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by roman on 05.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UserValidatorTest {
    @Autowired
    UserValidator userValidator;
    User validUser;
    User invalidLoginUser;
    User invalidEmailUser;

    @Before
    public void setup() {
        validUser = new User(1, "test", "nickname", "test@gmail.com");
        invalidLoginUser = new User(1, "user", "nickname", "test@gmail.com");
        invalidEmailUser = new User(1, "user", "nickname", "user@mail.com");
    }

    @Test
    public void testValidUser() {
        Errors errors = new BeanPropertyBindingResult(validUser, "user");
        userValidator.validate(validUser, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testNotValidEmail() {
        Errors errors = new BeanPropertyBindingResult(invalidEmailUser, "user");
        userValidator.validate(invalidEmailUser, errors);
        assertTrue(errors.hasErrors());
    }

    @Test
    public void testNotValidLogin() {
        Errors errors = new BeanPropertyBindingResult(invalidLoginUser, "user");
        userValidator.validate(invalidLoginUser, errors);
        assertTrue(errors.hasErrors());
    }
}
