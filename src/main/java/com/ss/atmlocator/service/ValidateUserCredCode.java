package com.ss.atmlocator.service;

/**
* This class contains with enums of error keys and codes for user credentials verification
*/
public final class ValidateUserCredCode {

    private ValidateUserCredCode(){}

    public static enum ValidationResult{
        INVALID_LOGIN,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        EMAIL_ALREADY_REGISTERED,
        LOGIN_ALREADY_REGISTERED
    }

    public static enum ValidationKey{
        EMAIL,
        LOGIN,
        PASSWORD
    }

}
