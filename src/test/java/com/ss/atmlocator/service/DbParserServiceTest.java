package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
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
import static org.junit.Assert.assertThat;

/**
 * Created by maks on 07.01.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/test-configuration.xml")
public class DbParserServiceTest  {
    private MockMvc mockMvc;

    @Mock
    IAtmsDAO atmsDAOMock;
    @Mock
    IBanksDAO banksDAOMock;
    @InjectMocks
    DbParserService dbParserService;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    /*@Test
    private void testCompareAtm(){
        AtmOffice atmOne = new AtmOffice();
        atmOne.setType(AtmOffice.AtmType.IS_ATM);
        atmOne.setAddress("st. Horunja 45");

        AtmOffice atmTwo = new AtmOffice();
        atmTwo.setType(AtmOffice.AtmType.IS_ATM);
        atmTwo.setAddress("st. Horunja 45");

    }*/
}
