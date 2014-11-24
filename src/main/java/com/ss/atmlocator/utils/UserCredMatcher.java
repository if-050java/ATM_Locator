package com.ss.atmlocator.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCredMatcher {

    private String mailRegExp;
    private String loginRegExp;
    private String passwordRegExp;

    private Pattern emailPattern;
    private Matcher emailMatcher;

    private Pattern loginPattern;
    private Matcher loginMatcher;

    private Pattern passwordPattern;
    private Matcher passwordMatcher;

    public UserCredMatcher(String mailRegExp, String loginRegExp, String passwordRegExp) {

        this.mailRegExp = mailRegExp;
        this.loginRegExp = loginRegExp;
        this.passwordRegExp = passwordRegExp;

        emailPattern = Pattern.compile(mailRegExp);
        loginPattern = Pattern.compile(loginRegExp);
        passwordPattern = Pattern.compile(passwordRegExp);
    }

    public boolean validateEmail(final String email){

        emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();

    }

    public boolean validateLogin(final String login){

        loginMatcher = loginPattern.matcher(login);
        return loginMatcher.matches();

    }

    public boolean validatePassword(final String password){

        passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();

    }
}
