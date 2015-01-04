package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Role;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.NoResultException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.*;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.UserStatus.ENABLED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import static org.hamcrest.CoreMatchers.any;

/**
 * Created by DrBAX_000 on 04.01.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UsersControllerTest {
    @Mock
    UserService userService;

    @Mock
    Validator userValidator;

    @Mock
    Validator imageValidator;

    @InjectMocks
    UsersRestController usersRestController;

    MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        //setup userService

        User user = new User(1,"user", "user@mail.com", "sdfsdfghdfghdfghj", ENABLED);
        user.setName("User");
        user.setAvatar("defaultUserAvatar.jpg");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        user.setRoles(roles);

        User admin = new User(2,"admin", "admin@mail.com", "sdfsdfghdfghdfghj", ENABLED);
        admin.setName("Admin");
        admin.setAvatar("defaultUserAvatar.jpg");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(new Role("ADMIN"));
        admin.setRoles(adminRoles);

        when(userService.getUser(1)).thenReturn(user);
        when(userService.getUser(2)).thenReturn(admin);
        when(userService.getUser("user")).thenReturn(user);
        when(userService.getUser("admin")).thenReturn(admin);
        when(userService.getUser("user1")).thenThrow(NoResultException.class);

        doThrow(NoResultException.class).when(userService).deleteUser(3);




        List<String> names = new ArrayList<>();
        names.add("user");
        names.add("user1");
        when(userService.getNames("us")).thenReturn(names);
        when(userService.getNames("a")).thenReturn(Collections.EMPTY_LIST);

        Set<AtmOffice> favorites = new HashSet<>();
        favorites.add(new AtmOffice("Івано-Франківськ", IS_ATM));
        when(userService.getFavorites(1)).thenReturn(favorites);
        when(userService.getFavorites(2)).thenReturn(Collections.EMPTY_SET);

        //setup userValidator
        //doThrow(ValidationException.class).when(userValidator).validate(any(User.class), any(BindingResult.class));

        mockMvc = MockMvcBuilders.standaloneSetup(usersRestController).build();
    }

    @Test
    public void getUserNamesTest() throws Exception {

        mockMvc.perform(get("/users/").param("query", "us"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.suggestions", hasSize(2)))
                .andExpect(jsonPath("$.suggestions.[0]", is("user")))
                .andExpect(jsonPath("$.suggestions.[1]", is("user1")));

        mockMvc.perform(get("/users/").param("query", "a"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.suggestions", hasSize(0)));
    }

    @Test
    public void findUserTest() throws Exception {

        mockMvc.perform(get("/users/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("user")))
                .andExpect(jsonPath("$.email",is("user@mail.com")))
                .andExpect(jsonPath("$.name",is("User")))
                .andExpect(jsonPath("$.avatar", is("defaultUserAvatar.jpg")))
                .andExpect(jsonPath("$.roles.[0].name", is("USER")));

        mockMvc.perform(get("/users/user1"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteUserTest() throws Exception{
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/users/2"))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(delete("/users/3"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void editUserTest() throws Exception {
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "admin";
            }
        };
        mockMvc.perform(patch("/users/2")
                        .content("{\"login\":\"admin1\"}")
                        .param("generatePassword", "false")
                        .principal(principal)
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/users/1")
                .content("{\"name\":\"User\"}")
                .param("generatePassword", "true")
                .principal(principal)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(userService, times(1)).doAutoLogin(anyString());
        verify(userValidator, times(2)).validate(any(User.class), any(Errors.class));
    }
}
