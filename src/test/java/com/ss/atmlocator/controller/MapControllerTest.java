package com.ss.atmlocator.controller;

import com.ss.atmlocator.service.ATMService;
import com.ss.atmlocator.service.AtmNetworksService;
import com.ss.atmlocator.service.BanksService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.hasSize;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by roman on 05.01.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class MapControllerTest {
    @Mock
    BanksService banksService;
    @Mock
    ATMService atmService;
    @InjectMocks
    MapController controllerUnderTest;
    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    final long MOCK_NETWORK_ID = 1;
    final double USER_LAT = 48.93310088;
    final double USER_LONG = 24.70705032;
    final int RADIUS = 5000;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllPrivatBankAtms() throws Exception {
        final int MOCK_BANK_ID = 288;
        final int MOCK_ATMS_COUNT = 8;

        final boolean SHOW_ATMS = true;
        final boolean SHOW_OFFICES = true;

        ResultActions resultActions = mockMvc.perform(get("/map/getATMs")
                .param("networkId", String.valueOf(MOCK_NETWORK_ID))
                .param("bankId", String.valueOf(MOCK_BANK_ID))
                .param("userLat", String.valueOf(USER_LAT))
                .param("userLng", String.valueOf(USER_LONG))
                .param("radius", String.valueOf(RADIUS))
                .param("showAtms", String.valueOf(SHOW_ATMS))
                .param("showOffices", String.valueOf(SHOW_OFFICES))).andDo(print());
        resultActions.andExpect(jsonPath("$", hasSize(MOCK_ATMS_COUNT)));

    }
    @Test
    public void testGetAllPrivatNetworkAtms() throws Exception {
        final int MOCK_ATMS_COUNT = 10;

        final boolean SHOW_ATMS = true;
        final boolean SHOW_OFFICES = true;

        ResultActions resultActions = mockMvc.perform(get("/map/getATMs")
                .param("networkId", String.valueOf(MOCK_NETWORK_ID))
                .param("userLat", String.valueOf(USER_LAT))
                .param("userLng", String.valueOf(USER_LONG))
                .param("radius", String.valueOf(RADIUS))
                .param("showAtms", String.valueOf(SHOW_ATMS))
                .param("showOffices", String.valueOf(SHOW_OFFICES))).andDo(print());
        resultActions.andExpect(jsonPath("$", hasSize(MOCK_ATMS_COUNT)));

    }

    @Test
    public void testGetPrivatNetworkShowNothing() throws Exception {
        final int MOCK_ATMS_COUNT = 0;
        final boolean SHOW_ATMS = false;
        final boolean SHOW_OFFICES = false;

        ResultActions resultActions = mockMvc.perform(get("/map/getATMs")
                .param("networkId", String.valueOf(MOCK_NETWORK_ID))
                .param("userLat", String.valueOf(USER_LAT))
                .param("userLng", String.valueOf(USER_LONG))
                .param("radius", String.valueOf(RADIUS))
                .param("showAtms", String.valueOf(SHOW_ATMS))
                .param("showOffices", String.valueOf(SHOW_OFFICES))).andDo(print());
        resultActions.andExpect(jsonPath("$", hasSize(MOCK_ATMS_COUNT)));

    }

    @Test
    public void testGetBanksByNetworkPrivat() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/map/getBanksByNetwork")
                .param("network_id", String.valueOf(MOCK_NETWORK_ID))).andDo(print());
        resultActions.andExpect(jsonPath("$", hasSize(2)));
    }
}
