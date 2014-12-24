package com.ss.atmlocator.parser.coordEncoder;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public final class AddressWorker {

    private AddressWorker(){}

    private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json";

    public static GoogleResponse convertToLatLong(String fullAddress) throws IOException {
        URL url = new URL(URL + "?address="
                + URLEncoder.encode(fullAddress, "UTF-8") + "&sensor=false");
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream() ;
        ObjectMapper mapper = new ObjectMapper();
        GoogleResponse response = (GoogleResponse)mapper.readValue(in,GoogleResponse.class);
        in.close();
        return response;
    }

}
