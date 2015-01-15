package com.ss.atmlocator.utils;

import java.util.regex.Pattern;

/**
 *  Utility class for regexp checking login,password,email
 */

public class UserCredMatcher {

    private Pattern emailPattern;
    private Pattern loginPattern;
    private Pattern passwordPattern;
    private Pattern nickNamePattern;

    /* Constructor with regexp as params*/
    public UserCredMatcher(String mailRegExp, String loginRegExp, String passwordRegExp, String nickNameRegExp) {
        emailPattern = Pattern.compile(mailRegExp);
        loginPattern = Pattern.compile(loginRegExp);
        passwordPattern = Pattern.compile(passwordRegExp);
        nickNamePattern = Pattern.compile(nickNameRegExp);
    }

    /* check email Returns true if email is valid */
    public boolean validateEmail(final String email){
        return emailPattern.matcher(email).matches();
    }

    /* check login Returns true if login is valid*/
    public boolean validateLogin(final String login){
        return loginPattern.matcher(login).matches();
    }

    /* check login. Returns true if password is valid */
    public boolean validatePassword(final String password){
        return passwordPattern.matcher(password).matches();
    }

    /* check nickname. Returns true if nickname is valid */
    public boolean validateNickName(final String nickName){
        return nickNamePattern.matcher(nickName).matches();
    }
}
