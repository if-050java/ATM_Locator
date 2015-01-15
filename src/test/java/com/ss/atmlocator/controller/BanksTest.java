package com.ss.atmlocator.controller;

import com.ss.atmlocator.entity.AtmNetwork;
import com.ss.atmlocator.entity.Bank;
import com.ss.atmlocator.service.AtmNetworksService;
import com.ss.atmlocator.service.BanksService;
import com.ss.atmlocator.utils.Constants;
import com.ss.atmlocator.utils.OutResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * AdminBanksController MVC test
 * Created by Olavin on 28.12.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class BanksTest {

    @Mock
    BanksService banksService;
    @Mock
    AtmNetworksService atmNetworksService;
    @InjectMocks
    AdminBanksController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).setViewResolvers(viewResolver).build();

    }

    @Test
    public void adminBanks() throws Exception {
        Principal user = new Principal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };

        when(atmNetworksService.getNetworksList()).thenReturn(new ArrayList<AtmNetwork>());

        mockMvc.perform(get("/adminBanks").principal(user))
                .andExpect(status().isOk())
                .andExpect(view().name("adminBanks"))
                .andExpect(model().attribute("userName", "testUser"))
                .andExpect(model().attribute("active", "adminBanks"))
                .andExpect(model().attributeExists("networks"));
    }

    @Test
    public void adminBankCreateNew() throws Exception {
        Principal user = new Principal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };

        when(banksService.newBank()).thenReturn(new Bank());
        when(atmNetworksService.getNetworksList()).thenReturn(new ArrayList<AtmNetwork>());

        ResultActions resultActions = mockMvc.perform(get("/adminBankCreateNew").principal(user));

        resultActions.andExpect(status().isOk())
                .andExpect(view().name("adminBankEdit"))
                .andExpect(model().attribute("userName", "testUser"))
                .andExpect(model().attribute("atm_count", 0))
                .andExpect(model().attribute("active", "adminBanks"))
                .andExpect(model().attributeExists("networks"))
                .andExpect(model().attributeExists("bank"));

        Object obj = resultActions.andReturn().getModelAndView().getModel().get("bank");
        assertTrue(obj instanceof Bank);
        Bank testBank = (Bank) obj;
        assertEquals(testBank.getId(), 0);
        assertNull(testBank.getName());
    }

    @Test
    public void adminBankEdit() throws Exception {
        Principal user = new Principal() {
            @Override
            public String getName() {
                return "testUser";
            }
        };

        final int MOCK_BANK_ID = 123;
        final long MOCK_ATMS_COUNT = 321;

        Bank mockBank = new Bank();
        mockBank.setId(MOCK_BANK_ID);

        when(banksService.getBank(MOCK_BANK_ID)).thenReturn(mockBank);
        when(atmNetworksService.getNetworksList()).thenReturn(new ArrayList<AtmNetwork>());
        when(banksService.getBankAtmsCount(MOCK_BANK_ID)).thenReturn(MOCK_ATMS_COUNT);

        ResultActions resultActions = mockMvc.perform(get("/adminBankEdit")
                .param("bank_id", String.valueOf(MOCK_BANK_ID))
                .principal(user));

        resultActions.andExpect(status().isOk())
                .andExpect(view().name("adminBankEdit"))
                .andExpect(model().attribute("userName", "testUser"))
                .andExpect(model().attribute("atm_count",MOCK_ATMS_COUNT))
                .andExpect(model().attribute("active", "adminBanks"))
                .andExpect(model().attributeExists("networks"))
                .andExpect(model().attributeExists("bank"));

        Object obj = resultActions.andReturn().getModelAndView().getModel().get("bank");
        assertTrue(obj instanceof Bank);
        Bank testBank = (Bank) obj;
        assertEquals(testBank, mockBank);
    }

    @Test
    public void adminBankSaveAjax() throws Exception {

        final int MOCK_BANK_ID = 123;
        final int MOCK_NETWORK_ID = 77;

        Bank mockBank = Mockito.mock(Bank.class);
        mockBank.setId(MOCK_BANK_ID);

        AtmNetwork mockNetwork = new AtmNetwork();
        mockNetwork.setId(MOCK_NETWORK_ID);

        OutResponse mockOutResponse = new OutResponse();
        mockOutResponse.setStatus(Constants.SUCCESS);

        ArgumentCaptor<Bank> argument = ArgumentCaptor.forClass(Bank.class);

        when(banksService.saveBank(any(Bank.class),
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(HttpServletRequest.class)))
                .thenReturn(mockOutResponse);

        when(atmNetworksService.getNetwork(MOCK_NETWORK_ID)).thenReturn(mockNetwork);

        MockMultipartFile image1 = new MockMultipartFile("imageLogo", "logo", "text/plain", "some xml".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("iconAtmFile", "iconAtm", "text/plain", "some other type".getBytes());
        MockMultipartFile image3 = new MockMultipartFile("iconOfficeFile", "iconOffice", "text/plain", "iconOffice".getBytes());


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/adminBankSaveAjax")
                .file(image1)
                .file(image2)
                .file(image3)
                .param("network_id", String.valueOf(MOCK_NETWORK_ID))
                .accept(APPLICATION_JSON)
                .param("id", String.valueOf(MOCK_BANK_ID))); //bank id

        resultActions.andExpect(status().isOk());

        verify(banksService).saveBank(argument.capture(),
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(MultipartFile.class),
                any(HttpServletRequest.class));


        assertEquals(MOCK_BANK_ID, argument.getValue().getId());
        assertEquals(mockNetwork, argument.getValue().getNetwork());

    }


    @Test
    public void networksListAjax() throws Exception {
        when(atmNetworksService.getNetworksList()).thenReturn(new ArrayList<AtmNetwork>());
        ResultActions resultActions = mockMvc.perform(get("/networksListAjax").accept(APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        String content = resultActions.andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = (JSONArray) JSONValue.parse(content);
        assertNotNull("Content can't be parsed as JSON",jsonArray);
    }

    @Test
    public void banksListAjax() throws Exception {
        when(banksService.getBanksList()).thenReturn(new ArrayList<Bank>());
        ResultActions resultActions = mockMvc.perform(get("/banksListAjax").accept(APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));

        String content = resultActions.andReturn().getResponse().getContentAsString();
        JSONArray jsonArray = (JSONArray) JSONValue.parse(content);
        assertNotNull("Content can't be parsed as JSON",jsonArray);
    }

}
