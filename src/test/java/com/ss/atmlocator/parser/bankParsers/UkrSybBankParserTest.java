package com.ss.atmlocator.parser.bankParsers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class, Integer.class})
public class UkrSybBankParserTest {

    private Document atms;
    private Properties testProperties = new Properties();


    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File atmsFile = new File(classLoader.getResource("ukrSybBankMock.data").getFile());
        atms = Jsoup.parse(atmsFile, "UTF-8");
        File testPropertiesFile = new File(classLoader.getResource("ukrSybBankParserTest.properties").getFile());
        InputStream is = new FileInputStream(testPropertiesFile);
        testProperties.load(is);
    }

    @Test
    public void parseTest() throws Exception {
        mockStatic(Jsoup.class);
        Connection connection = mock(Connection.class);
        when(Jsoup.connect(anyString())).thenReturn(connection);
        when(connection.userAgent(anyString())).thenReturn(connection);
        when(connection.data(any(Map.class))).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        when(connection.get()).thenReturn(atms);

        UkrSybBankParser ukrSybBankParser = spy(new UkrSybBankParser());
        MemberModifier.field(UkrSybBankParser.class, "parserProperties")
                .set(ukrSybBankParser, testProperties);
        assertEquals(16, ukrSybBankParser.parse().size());
    }

}
