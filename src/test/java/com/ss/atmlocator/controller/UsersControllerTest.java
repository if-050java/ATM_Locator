package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.*;
import com.ss.atmlocator.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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


/**
 * Created by DrBAX_000 on 04.01.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class UsersControllerTest {
    @Mock
    UserService userService;

    @Spy
    Validator userValidator = new Validator() {
        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }

        @Override
        public void validate(Object target, Errors errors) {
            User user = (User)target;
            if(user.getName() != null && user.getName().length()<4)
              errors.reject("error");
        }
    };

    @Mock
    Validator imageValidator;

    @InjectMocks
    UsersRestController usersRestController;

    MockMvc mockMvc;

    //Initialize entities for tests-------------------------------------------------------------------------------------
    Principal user = new Principal() {
        @Override
        public String getName() {
            return "user";
        }
    };
    Principal admin = new Principal() {
        @Override
        public String getName() {
            return "admin";
        }
    };
    Principal user1 = new Principal() {
        @Override
        public String getName() {
            return "user1";
        }
    };

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    @Before
    public void setup() throws MessagingException {
        MockitoAnnotations.initMocks(this);

        //setup entities for userService
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

        List<AtmComment> comments = new ArrayList<>();
        AtmComment comment = new AtmComment();
        comment.setUser(admin);
        comment.setId(1);
        comment.setText("xvn sdfgh");
        comments.add(comment);

        Set<AtmOffice> favorites = new HashSet<>();
        AtmOffice atmOffice = new AtmOffice("Івано-Франківськ", IS_ATM);
        atmOffice.setAtmComments(comments);
        favorites.add(atmOffice);
        AtmOffice atmOffice1 = new AtmOffice("Львів", IS_ATM);
        atmOffice1.setAtmComments(Collections.EMPTY_LIST);
        favorites.add(atmOffice1);

        when(userService.getUser(1)).thenReturn(user);
        when(userService.getUser(2)).thenReturn(admin);
        when(userService.getUser("user")).thenReturn(user);
        when(userService.getUser("admin")).thenReturn(admin);
        when(userService.getUser("user1")).thenThrow(NoResultException.class);

        doThrow(NoResultException.class).when(userService).deleteUser(3);

        doThrow(PersistenceException.class).when(userService).editUser(any(User.class), eq(true));

        doThrow(PersistenceException.class).when(userService).addFavorite(eq(1), anyInt());

        doThrow(PersistenceException.class).when(userService).deleteFavorite(eq(1), anyInt());

        List<String> names = new ArrayList<>();
        names.add("user");
        names.add("user1");
        when(userService.getNames("us")).thenReturn(names);
        when(userService.getNames("a")).thenReturn(Collections.EMPTY_LIST);

        when(userService.getFavorites(2)).thenReturn(favorites);
        when(userService.getFavorites(1)).thenThrow(PersistenceException.class);

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
                        .principal(principal)
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/users/1")
                        .content("{\"name\":\"User\"}")
                        .principal(principal)
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/users/1")
                .content("{\"name\":\"U\"}")
                .principal(principal)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotAcceptable());

        mockMvc.perform(patch("/users/1")
                        .content("{\"name\":\"User\"}")
                        .param("generatePassword", "true")
                        .principal(principal)
                        .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());

        doThrow(MessagingException.class).when(userService).editUser(any(User.class), eq(false));
        mockMvc.perform(patch("/users/1")
                .content("{\"name\":\"User\"}")
                .param("generatePassword", "false")
                .principal(principal)
                .contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isServiceUnavailable());

        verify(userService, times(1)).doAutoLogin(anyString());
        verify(userValidator, times(5)).validate(any(User.class), any(Errors.class));
    }

    @Test
    public void getFavoritesTest() throws Exception {

        mockMvc.perform(get("/users/favorites/").principal(admin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.", hasSize(2)));

        mockMvc.perform(get("/users/favorites/").principal(user1))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/users/favorites/").principal(user))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addFavoriteTest() throws Exception {
        mockMvc.perform(put("/users/favorites/125").principal(user))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(put("/users/favorites/125").principal(admin))
                .andExpect(status().isOk());

        verify(userService, times(2)).addFavorite(anyInt(), anyInt());
    }

    @Test
    public void deleteFavoriteTest() throws Exception {
        mockMvc.perform(delete("/users/favorites/125").principal(user))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(delete("/users/favorites/125").principal(admin))
                .andExpect(status().isOk());

        verify(userService, times(2)).deleteFavorite(anyInt(), anyInt());
    }
}
