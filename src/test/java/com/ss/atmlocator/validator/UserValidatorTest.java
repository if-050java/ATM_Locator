package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

/**
 * Created by roman on 05.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UserValidatorTest {
    private static final String VALIDATED_OBJ_NAME = "user";
    @Autowired
    UserValidator userValidator;
    User validUser;
    User invalidLoginUser;
    User invalidEmailUser;
    User loginExists;
    User emailExists;
    User emailAndLoginExists;
    @Autowired
    private MessageSource messages;

    @Before
    public void setup() {
        validUser = new User(1, "test", "nickname", "test@gmail.com");
        invalidLoginUser = new User(1, "us", "nickname", "test@gmail.com");
        invalidEmailUser = new User(1, "user", "nickname", "user123dfwfewfwfw");
        loginExists = new User(1, "user", "nickname", "test@gmail.com");
        emailExists = new User(1, "test", "nickname", "user@mail.com");
        emailAndLoginExists = new User(1, "user", "nickname", "user@mail.com");
    }

    @Test
    public void testValidUser() {
        Errors errors = new BeanPropertyBindingResult(validUser, VALIDATED_OBJ_NAME);
        userValidator.validate(validUser, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testNotValidEmail() {
        Errors errors = new BeanPropertyBindingResult(invalidEmailUser, VALIDATED_OBJ_NAME);
        userValidator.validate(invalidEmailUser, errors);
        assertTrue(errors.hasErrors());
        String actualMessage = errors.getFieldError(Constants.USER_EMAIL).getCode();
        String expectedMessage = messages.getMessage("invalid.email", null, Locale.ENGLISH);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testNotValidLogin() {
        Errors errors = new BeanPropertyBindingResult(invalidLoginUser, VALIDATED_OBJ_NAME);
        userValidator.validate(invalidLoginUser, errors);
        assertTrue(errors.hasErrors());
        String actualMessage = errors.getFieldError(Constants.USER_LOGIN).getCode();
        String expectedMessage = messages.getMessage("invalid.login", null, Locale.ENGLISH);
        assertEquals(actualMessage, expectedMessage);
    }
    @Test
    public void testLoginExists() {
        Errors errors = new BeanPropertyBindingResult(loginExists, VALIDATED_OBJ_NAME);
        userValidator.validate(loginExists, errors);
        assertTrue(errors.hasErrors());
        String actualMessage = errors.getFieldError(Constants.USER_LOGIN).getCode();
        String expectedMessage = messages.getMessage("login.exists", null, Locale.ENGLISH);
        assertEquals(actualMessage, expectedMessage);
    }
    @Test
    public void testEmailExists() {
        Errors errors = new BeanPropertyBindingResult(emailExists, VALIDATED_OBJ_NAME);
        userValidator.validate(emailExists, errors);
        assertTrue(errors.hasErrors());
        String actualMessage = errors.getFieldError(Constants.USER_EMAIL).getCode();
        String expectedMessage = messages.getMessage("email.exists", null, Locale.ENGLISH);
        assertEquals(actualMessage, expectedMessage);
    }
    @Test
    public void testEmailAndLoginExists() {
        Errors errors = new BeanPropertyBindingResult(emailAndLoginExists, VALIDATED_OBJ_NAME);
        userValidator.validate(emailAndLoginExists, errors);

        String loginExists = errors.getFieldError(Constants.USER_LOGIN).getCode();
        String emailExists = errors.getFieldError(Constants.USER_EMAIL).getCode();
        List<String> actualMessages = Arrays.asList(loginExists,emailExists);
        String email_exists = messages.getMessage("email.exists", null, Locale.ENGLISH);
        String invalid_email = messages.getMessage("login.exists", null, Locale.ENGLISH);
        List<String> expectedMessages = Arrays.asList(email_exists, invalid_email);

        assertTrue(errors.hasErrors());
        assertTrue(actualMessages.containsAll(expectedMessages));
    }
}
