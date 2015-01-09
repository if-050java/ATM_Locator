package com.ss.atmlocator.service;

import com.ss.atmlocator.dao.IAtmsDAO;
import com.ss.atmlocator.dao.IBanksDAO;
import com.ss.atmlocator.entity.AtmOffice;
import com.ss.atmlocator.entity.Bank;
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

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    IAtmsDAO atmsDAO;
    @Mock
    IBanksDAO banksDAOMock;
    @InjectMocks
    DbParserService dbParserService;

    AtmOffice atmOne;
    AtmOffice atmTwo;
    List<AtmOffice> atmFromDatabase;
    List<AtmOffice> newAtm;
    Bank someBank;
    
    private List<AtmOffice> prepareAtmFromDB(){
       List<AtmOffice> resultList = new ArrayList<>();
        AtmOffice atm1 = new AtmOffice();
        atm1.setAddress("вул. Черновецька 34");
        atm1.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm2 = new AtmOffice();
        atm2.setAddress("вул. Черновецька 44");
        atm2.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm3 = new AtmOffice();
        atm3.setAddress("вул. Богдана 4");
        atm3.setType(AtmOffice.AtmType.IS_ATM_OFFICE);

        AtmOffice atm4 = new AtmOffice();
        atm4.setAddress("вул. Геріди 32");
        atm4.setType(AtmOffice.AtmType.IS_ATM_OFFICE);

        resultList.add(atm1);
        resultList.add(atm2);
        resultList.add(atm3);
        resultList.add(atm4);
        
        return resultList;
    }
    private List<AtmOffice> prepareNewAtm(){
        List<AtmOffice> resultList = new ArrayList<>();
        AtmOffice atm1 = new AtmOffice();
        atm1.setAddress("вул. Черновецька 34");
        atm1.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm2 = new AtmOffice();
        atm2.setAddress("вул. Черновецька 44");
        atm2.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm3 = new AtmOffice();
        atm3.setAddress("вул. Богдана 4");
        atm3.setType(AtmOffice.AtmType.IS_ATM);


        AtmOffice atm4 = new AtmOffice();
        atm4.setAddress("вул. Хоругви 58");
        atm4.setType(AtmOffice.AtmType.IS_ATM);

        resultList.add(atm1);
        resultList.add(atm2);
        resultList.add(atm3);
        resultList.add(atm4);


        return resultList;
    }
    private List<AtmOffice> expectedAtmList(){
        List<AtmOffice> resultList = new ArrayList<>();
        AtmOffice atm1 = new AtmOffice();
        atm1.setAddress("вул. Черновецька 34");
        atm1.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm2 = new AtmOffice();
        atm2.setAddress("вул. Черновецька 44");
        atm2.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm3 = new AtmOffice();
        atm3.setAddress("вул. Богдана 4");
        atm3.setType(AtmOffice.AtmType.IS_ATM);

        AtmOffice atm4 = new AtmOffice();
        atm4.setAddress("вул. Геріди 32");
        atm4.setType(AtmOffice.AtmType.IS_ATM_OFFICE);
        AtmOffice atm5 = new AtmOffice();
        atm5.setAddress("вул. Хоругви 58");
        atm5.setType(AtmOffice.AtmType.IS_ATM);

        resultList.add(atm1);
        resultList.add(atm2);
        resultList.add(atm3);
        resultList.add(atm4);
        resultList.add(atm5);

        return resultList;
    }
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        atmFromDatabase = prepareAtmFromDB();
        newAtm = prepareNewAtm();
        
        someBank = new Bank();
        someBank.setId(1);
        someBank.setWebSite("sdf.sdfsd");


        atmOne = new AtmOffice();
        atmOne.setType(AtmOffice.AtmType.IS_ATM);
        atmOne.setAddress("st. Horunja 45");

        atmTwo = new AtmOffice();
        atmTwo.setType(AtmOffice.AtmType.IS_OFFICE);
        atmTwo.setAddress("st. Horunja 45");
    }
    @Test
    public void testCompareAtm_IfTypeAtmOfficeNotEquals(){

        dbParserService.compareAtm(atmOne, atmTwo);
        System.out.println(atmOne.getType());
        assertEquals(AtmOffice.AtmType.IS_OFFICE, atmOne.getType());
    }
    @Test
    public void testCompareAtm_IfTypeAtmOfficeEquals(){
        atmOne.setType(AtmOffice.AtmType.IS_ATM);
        atmTwo.setType(AtmOffice.AtmType.IS_ATM);
        dbParserService.compareAtm(atmOne, atmTwo);
        assertEquals(AtmOffice.AtmType.IS_ATM, atmOne.getType());
    }
    @Test
    public void testCompareAtm_IfAtmOfficeEquals(){

        atmTwo.setType(AtmOffice.AtmType.IS_ATM);
        atmTwo.setAddress("st. Horunja 45");
        boolean actual =dbParserService.compareAtm(atmOne, atmTwo);
        boolean expected = true;
        assertEquals(expected, actual );
    }
    @Test
    public void testCompareAtm_IfAtmOfficeNotEquals(){
        atmOne.setType(AtmOffice.AtmType.IS_ATM);
        atmTwo.setType(AtmOffice.AtmType.IS_ATM);
        atmTwo.setAddress("st. Horunja 454");
        boolean actual =dbParserService.compareAtm(atmOne, atmTwo);
        boolean expected = false;
        assertEquals(expected, actual );
    }
    @Test
    public void testUpdate_ifDataIsCorrect(){
        // when(usersDAO.getUser(anyInt())).thenReturn(savedUser);
        
        when(atmsDAO.getBankAtms(anyInt())).thenReturn(atmFromDatabase);

        List<AtmOffice> actualAtmOffices=dbParserService.update(newAtm,1);
        List<AtmOffice> expectedAtmOffices=expectedAtmList();
        for (int i =0;i<actualAtmOffices.size();i++){
            System.out.println(actualAtmOffices.get(i)+"==="+expectedAtmOffices.get(i));
        }
        for (int i = 0; i < expectedAtmOffices.size(); i++) {
            assertEquals(expectedAtmOffices.get(i), actualAtmOffices.get(i));
        }
        //assertSame(expectedAtmOffices, actualAtmOffices);

    }

}
