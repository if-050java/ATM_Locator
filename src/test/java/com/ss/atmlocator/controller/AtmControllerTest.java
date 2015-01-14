package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmComment;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.service.ATMService;
import com.ss.atmlocator.service.CommentsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.ss.atmlocator.entity.AtmOffice.AtmType.IS_ATM;
import static com.ss.atmlocator.entity.UserStatus.ENABLED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class AtmControllerTest {

    @Mock
    private CommentsService commentsService;
    @Mock
    private ATMService atmService;
    @InjectMocks
    private AtmController atmController;

    private MockMvc mockMvc;

    User user;
    User admin;
    Principal userPrincipal;
    Principal adminPrincipal;
    AtmOffice atmOffice;
    AtmOffice atmOffice1;
    Set<AtmComment> comments;
    AtmComment comment, comment1;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(atmController).build();

        user = new User(1,"user", "user@mail.com", "password", ENABLED);
        admin = new User(2,"admin", "admin@mail.com", "password", ENABLED);

        userPrincipal = new Principal() {
            @Override
            public String getName() {
                return "user";
            }
        };
        adminPrincipal = new Principal() {
            @Override
            public String getName() {
                return "admin";
            }
        };

        comment = new AtmComment();
        comment.setId(1);
        comment.setText("Some text");
        comment.setUser(user);
        comment.setAtmOffice(atmOffice);
        comment1 = new AtmComment();
        comment1.setId(2);
        comment1.setText("Some text");
        comment1.setUser(admin);
        comment1.setAtmOffice(atmOffice);
        comments = new HashSet<>();
        comments.add(comment);
        comments.add(comment1);

        atmOffice = new AtmOffice("Івано-Франківськ", IS_ATM);
        atmOffice.setId(1);
        atmOffice.setAtmComments(comments);
        atmOffice1 = new AtmOffice("Львів", IS_ATM);
        atmOffice1.setAtmComments(Collections.<AtmComment>emptySet());
        atmOffice1.setId(2);
    }

    @Test
    public void getCommentsTest() throws Exception {
        when(atmService.getAtmById(1)).thenReturn(atmOffice);
        when(atmService.getAtmById(2)).thenReturn(atmOffice1);
        when(atmService.getAtmById(3)).thenThrow(NoResultException.class);

        //when ATM has comments
        mockMvc.perform(get("/atms/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.", hasSize(2)))
                .andExpect(jsonPath("$.[0].text", is("Some text")))
                .andExpect(jsonPath("$.[1].user.login", is("admin")));

        //when ATM hasn't any comments
        mockMvc.perform(get("/atms/2/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.", hasSize(0)));

        //when atm not exist
        mockMvc.perform(get("/atms/3/comments"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCommentTest() throws Exception {
        doThrow(NoResultException.class).when(commentsService).addComment(anyString(), eq(2), anyString());
        doThrow(PersistenceException.class).when(commentsService).addComment(eq("user"), anyInt(), anyString());

        //when ATM exists
        mockMvc.perform(put("/atms/1/comments")
                        .principal(adminPrincipal)
                        .content("Some text"))
                .andExpect(status().isOk());

        //when atm not exists
        mockMvc.perform(put("/atms/2/comments")
                .principal(adminPrincipal)
                .content("Some text"))
                .andExpect(status().isNotFound());

        //when db exception
        mockMvc.perform(put("/atms/1/comments")
                .principal(userPrincipal)
                .content("Some text"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deletecommentTest() throws Exception{
        when(commentsService.getComment(1)).thenReturn(comment);
        when(commentsService.getComment(2)).thenReturn(comment1);
        doThrow(NoResultException.class).when(commentsService).getComment(eq(3));
        doThrow(PersistenceException.class).when(commentsService).getComment(eq(4));

        //when user try to delete own comment
        mockMvc.perform(delete("/atms/1/comments/1").principal(userPrincipal))
                .andExpect(status().isOk());

        //when user try to delete comment of other user
        mockMvc.perform(delete("/atms/1/comments/1").principal(adminPrincipal))
                .andExpect(status().isNotAcceptable());

        //ven comment not exist
        mockMvc.perform(delete("/atms/1/comments/3").principal(userPrincipal))
                .andExpect(status().isNotFound());

        //when db exception
        mockMvc.perform(delete("/atms/1/comments/4").principal(userPrincipal))
                .andExpect(status().isInternalServerError());
    }
}
