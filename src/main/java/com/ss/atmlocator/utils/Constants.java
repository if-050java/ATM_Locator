package com.ss.atmlocator.utils;

/**
 * Created by Vasyl Danylyuk on 29.11.2014.
 */
public class Constants {
    //Request parameters for finding users
    public static final String FIND_BY = "findBy";
    public static final String FIND_VALUE = "findValue";
    //Request parameters for user fields
    public static final String USER_ID = "id";
    public static final String USER_LOGIN = "login";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ENABLED = "enabled";
    public static final String USER_AVATAR = "avatar";
    //Operation names
    public static final String DELETE = "delete";
    public static final String UPDATE = "update";
    public static final String SEND_EMAIL = "email_templates";
    //Results
    public static final String SUCCESS = "SUCCESS";
    public static final String INFO = "INFO";
    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";
    //Template names for creating email messages
    public static final String FULL_UPDATE_TEMPLATE = "full_update_template";
    public static final String UPDATE_TEMPLATE_WITHOUT_PASSWORD = "update_template_without_password";


}
