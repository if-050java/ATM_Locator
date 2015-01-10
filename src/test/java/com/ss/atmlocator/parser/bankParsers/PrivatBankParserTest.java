package com.ss.atmlocator.parser.bankParsers;

import com.ss.atmlocator.entity.AtmOffice;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith( PowerMockRunner.class )
@PrepareForTest(Jsoup.class)
public class PrivatBankParserTest {

    private Document atms;
    private Document offices;
    private Map<String, String> params = new HashMap<>();
    private Properties addresses;



    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File atmsFile = new File(classLoader.getResource("privatBankResponse.atms").getFile());
        File officesFile = new File(classLoader.getResource("privatBankResponse.offices").getFile());
        atms = Jsoup.parse(atmsFile, "UTF-8");
        offices = Jsoup.parse(officesFile, "UTF-8");

        File addressesFile = new File(classLoader.getResource("privatBankResponse.addresses").getFile());
        InputStream inputStream = new FileInputStream(addressesFile);
        addresses = new Properties();
        addresses.load(inputStream);
    }

    public String getAddressMock(String id){
        return addresses.getProperty(id);
    }

    @Test
    public void parseAtmsTest() throws IOException, NoSuchMethodException {
        mockStatic(Jsoup.class);

        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.userAgent(anyString())).thenReturn(connection);
        when(connection.data(any(Map.class))).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.post()).thenReturn(atms);

        PrivatBankParser privatBankParser = spy(new PrivatBankParser());
        doReturn("adfg").when(privatBankParser).getAddress(anyString());

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
        doReturn("adfg").when(privatBankParser).getAddress(anyString());

        privatBankParser.setParameter(params);
        List<AtmOffice> result = privatBankParser.parseOffices();

        assertEquals(9, result.size());
        assertEquals(41.757839202880860, result.get(2).getGeoPosition().getLatitude(), 0.0000001);
        verify(privatBankParser, times(9)).getAddress(anyString());
    }
}
