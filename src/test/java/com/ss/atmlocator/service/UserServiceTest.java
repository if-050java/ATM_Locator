package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IUsersDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.utils.EmailCreator;
import com.ss.atmlocator.utils.SendMails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.MessagingException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.ss.atmlocator.entity.UserStatus.DISABLED;
import static com.ss.atmlocator.entity.UserStatus.ENABLED;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UserServiceTest {
    @Mock
    private IUsersDAO usersDAO;
    @Mock
    private Md5PasswordEncoder passwordEncoder;
    @Mock
    private EmailCreator emailCreator;
    @Mock
    private SendMails sendMails;
    @Mock
    private UserDetailsManager userDetailsManager;
    @InjectMocks
    UserService userService;

    User savedUser;
    User partialUser;
    Set<Role> roles;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Role userRole = new Role("USER");
        roles = new HashSet<>();
        roles.add(userRole);
        initUser();
    }

    private void initUser(){
        partialUser = new User();
        savedUser = new User(1,"user", "user@mail.com", "0000000000000000", ENABLED);
    }

    @Test
    public void editUserTest() throws MessagingException {
        when(passwordEncoder.encodePassword(anyString(), eq(null))).thenReturn("1234567890123456");
        when(emailCreator.toUser(any(User.class), anyString())).thenReturn("Some message");


        //Change only nickname
        initUser();
        when(usersDAO.getUser(anyInt())).thenReturn(savedUser);
        partialUser.setName("Super User");
        User testUser = userService.editUser(partialUser, false);
        assertEquals("user", testUser.getLogin());
        assertEquals("Super User", testUser.getName());
        assertEquals("user@mail.com", testUser.getEmail());
        assertEquals("0000000000000000", testUser.getPassword());
        assertEquals(ENABLED, testUser.getEnabled());

        //try to disable user
        initUser();
        when(usersDAO.getUser(anyInt())).thenReturn(savedUser);
        partialUser = new User(0, null, null, null, DISABLED);
        testUser = userService.editUser(partialUser, false);
        assertEquals("user", testUser.getLogin());
        assertEquals("user@mail.com", testUser.getEmail());
        assertEquals("0000000000000000", testUser.getPassword());
        assertEquals(DISABLED, testUser.getEnabled());

        //Generate password
        initUser();
        when(usersDAO.getUser(anyInt())).thenReturn(savedUser);
        testUser = userService.editUser(partialUser, true);
        assertEquals("user", testUser.getLogin());
        assertEquals("user@mail.com", testUser.getEmail());
        assertEquals("1234567890123456", testUser.getPassword());
        assertEquals(ENABLED, testUser.getEnabled());

        //Change nickname and set password manually
        initUser();
        when(usersDAO.getUser(anyInt())).thenReturn(savedUser);
        partialUser.setPassword("somePassword");
        partialUser.setName("Super User");
        testUser = userService.editUser(partialUser, false);
        assertEquals("user", testUser.getLogin());
        assertEquals("Super User", testUser.getName());
        assertEquals("user@mail.com", testUser.getEmail());
        assertEquals("1234567890123456", testUser.getPassword());
        assertEquals(ENABLED, testUser.getEnabled());
    }
}
