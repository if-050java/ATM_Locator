package com.ss.atmlocator.utils;

/**
 * Created by us8610 on 11/25/2014.
 */
public class ErrorMessage {

    private String cause;
    private String message;

    public ErrorMessage(String cause, String message) {
        this.cause = cause;
        this.message = message;
    }
    public String getCause() {
        return cause;
    }
    public String getMessage() {
        return message;
    }

}