package com.ss.atmlocator.validator;

import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import com.ss.atmlocator.utils.UserCredMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by roman on 05.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UserValidatorTest {
    @InjectMocks
    UserValidator userValidator;
    private final static String ADMIN_ROLE_NAME = "ADMIN";
    private Role ADMIN_ROLE = new Role(ADMIN_ROLE_NAME);

    @Mock
    private UserCredMatcher userCredMatcher;

    @Mock
    private UserService userService;

    @Mock
    private MessageSource messages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidUser() {
        User user = new User("test", "nickname", "test@gmail.com", "1q2w3e4");
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(ADMIN_ROLE);
        user.setRoles(roles);
        when(userCredMatcher.validateEmail(anyString())).thenReturn(true);
        when(userCredMatcher.validateLogin(anyString())).thenReturn(true);
        when(userCredMatcher.validatePassword(anyString())).thenReturn(true);
        when(userCredMatcher.validateNickName(anyString())).thenReturn(true);
        when(userService.getUser(anyInt())).thenReturn(user);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void testNotValidEmail() {
        User user = new User("login", "nickname", "test@gmail.com", "1q2w3e4");
        Role adminRole = new Role();
        adminRole.setName("admin");
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(adminRole);
        user.setRoles(roles);
        when(userService.checkExistEmail(any(User.class))).thenReturn(true);
        when(userService.getUser(anyInt())).thenReturn(user);
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors);
        assertTrue(errors.hasErrors());
    }
}
