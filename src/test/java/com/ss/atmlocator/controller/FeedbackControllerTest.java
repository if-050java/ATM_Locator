package com.ss.atmlocator.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class FeedbackControllerTest {
    private MockMvc mockMvc;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private WebApplicationContext wac;
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
    @Test
    public void putFeedback() throws Exception {
        mockMvc.perform(put("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("feedback"));
    }

}