package com.ss.atmlocator.utils;

/**
 * Created by us8610 on 11/25/2014.
 */
public class ValidateUserFields {

    private String fieldName;
    private String message;

    public ValidateUserFields(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
    public String getFieldName() {
        return fieldName;
    }
    public String getMessage() {
        return message;
    }

}