package com.ss.atmlocator.validator;

/**
 * Created by us8610 on 11/26/2014.
 */
public class ValidationResult {
    public static final String INVALID_LOGIN = "Login is too short(min 4 letters) or has unsupported character";
    public static final String INVALID_EMAIL = "Email is not valid";
    public static final String INVALID_PASSWORD = "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit";
    public static final String LOGIN_ALREADY_EXIST = "Login already exists";
    public static final String EMAIL_ALREADY_EXIST = "Email already exists";
    public static final String NOTHING_TO_UPDATE = "Nothing to update";
    public static final String LIMIT_FILE_SIZE = "File size should be less than 600kb";
    public static final String INVALID_FILE_EXTENSION = "Invalid file extension (accepts png and jpg)";
}
