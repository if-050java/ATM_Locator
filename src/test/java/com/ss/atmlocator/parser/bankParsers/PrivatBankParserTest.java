package com.ss.atmlocator.parser.bankParsers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith( PowerMockRunner.class )
@PrepareForTest(Jsoup.class)
public class PrivatBankParserTest {

    private Document atms;
    private Document offices;
    private Map<String, String> params = new HashMap<>();
    private Properties testProperties = new Properties();



    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File atmsFile = new File(classLoader.getResource("privatBankResponse.atms").getFile());
        File officesFile = new File(classLoader.getResource("privatBankResponse.offices").getFile());
        atms = Jsoup.parse(atmsFile, "UTF-8");
        offices = Jsoup.parse(officesFile, "UTF-8");

        File testPropertiesFile = new File(classLoader.getResource("privatBankParserTest.properties").getFile());
        InputStream is = new FileInputStream(testPropertiesFile);
        testProperties.load(is);
    }


    /*@Test
    public void parseAtmsTest() throws IOException, NoSuchMethodException {
        mockStatic(Jsoup.class);

        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.userAgent(anyString())).thenReturn(connection);
        when(connection.data(any(Map.class))).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.post()).thenReturn(atms);

        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        doReturn("Some address").when(privatBankParser).getAddress(anyString());

        privatBankParser.setParameter(params);
        List<AtmOffice> result = privatBankParser.parseAtms();

        assertEquals(10, result.size());
        assertEquals(41.757839202880860, result.get(2).getGeoPosition().getLatitude(), 0.0000001);
        verify(privatBankParser, times(10)).getAddress(anyString());
    }

    @Test
    public void parseOfficesTest() throws IOException {
        mockStatic(Jsoup.class);

        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.userAgent(anyString())).thenReturn(connection);
        when(connection.data(any(Map.class))).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.post()).thenReturn(offices);

        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        doReturn("Some address").when(privatBankParser).getAddress(anyString());

        privatBankParser.setParameter(params);
        List<AtmOffice> result = privatBankParser.parseOffices();

        assertEquals(10, result.size());
        assertEquals(41.757839202880860, result.get(2).getGeoPosition().getLatitude(), 0.0000001);
        assertEquals(AtmState.DISABLED, result.get(8).getState());
        assertEquals(AtmState.NORMAL, result.get(5).getState());
        verify(privatBankParser, times(10)).getAddress(anyString());
    }

    @Test
    public void prepareAddressTest(){

        params.put("replace.regexp.1", ",[\\s]*\\d{5}");
        params.put("replace.value.1 =", "");
        params.put("replace.regexp.2", ",[\\s]*");
        params.put("replace.value.2", ", ");
        params.put("replace.regexp.3", "\\.[\\s]*");
        params.put("replace.value.3", ". ");

        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        privatBankParser.setParameter(params);
        String rawAddress      = "вул.  Юності,  23 с. Микитинці, Україна , 76000";
        String expectedAddress = "вул. Юності, 23 с. Микитинці, Україна";
        String preparedAddress = privatBankParser.prepareAddress(rawAddress);

        assertEquals(expectedAddress, preparedAddress);
    }

    @Test
    public void setParametersTest(){
        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        doReturn(testProperties).when(privatBankParser).loadProperties();

        Map<String, String> testParams = new HashMap<>();
        testParams.put("user.agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
        testParams.put("reading.timeout","500");

        privatBankParser.setParameter(testParams);
        assertEquals();
    }*/


    @Test
    public void parseTest() throws Exception {
        mockStatic(Jsoup.class);

        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.userAgent(anyString())).thenReturn(connection);
        when(connection.data(any(Map.class))).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.post()).thenReturn(atms);

        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        privatBankParser.setParameter(Collections.EMPTY_MAP);
        assertEquals(10, privatBankParser.parse().size());
    }

}
