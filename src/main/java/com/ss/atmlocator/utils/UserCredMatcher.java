package com.ss.atmlocator.utils;

import java.util.regex.Pattern;

/**
 *  Utility class for regexp checking login,password,email
 */

public class UserCredMatcher {

    private Pattern emailPattern;
    private Pattern loginPattern;
    private Pattern passwordPattern;

    /* Constructor with regexp as params*/
    public UserCredMatcher(String mailRegExp, String loginRegExp, String passwordRegExp) {
        emailPattern = Pattern.compile(mailRegExp);
        loginPattern = Pattern.compile(loginRegExp);
        passwordPattern = Pattern.compile(passwordRegExp);
    }

    /* check email Returns true if email id valid */
    public boolean validateEmail(final String email){
        return emailPattern.matcher(email).matches();
    }

    /* check login Returns true if login id valid*/
    public boolean validateLogin(final String login){
        return loginPattern.matcher(login).matches();
    }

    /* check login. Returns true if password id valid */
    public boolean validatePassword(final String password){
        return passwordPattern.matcher(password).matches();
    }
}
