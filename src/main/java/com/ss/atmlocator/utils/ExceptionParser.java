package com.ss.atmlocator.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Simple class for parsing exceptions
 */
public class ExceptionParser {

    private  ExceptionParser(){}

    public static String parseExceptions(Exception exp){
        StringWriter errors = new StringWriter();
        exp.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
