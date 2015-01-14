package com.ss.atmlocator.parser.bankParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by maks on 14.01.2015.
 */
@RunWith( PowerMockRunner.class )
@PrepareForTest(Jsoup.class)
public class AvalParserTest {
    private Document atms;
    private Document offices;
    private Map<String, String> params = new HashMap<>();
    private Properties testProperties = new Properties();

    @Before
    void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File atmsFile = new File(classLoader.getResource("avalAtms.atms").getFile());
        File officesFile = new File(classLoader.getResource("avalOffices.offices").getFile());
        atms = Jsoup.parse(atmsFile, "UTF-8");
        offices = Jsoup.parse(officesFile, "UTF-8");

        File testPropertiesFile = new File(classLoader.getResource("avalParserTest.properties").getFile());
        InputStream is = new FileInputStream(testPropertiesFile);
        testProperties.load(is);
    }
}
