package com.ss.atmlocator.utils;

public class CheckUserCredCode {

    private boolean exist;
    private String cause;

    public CheckUserCredCode(boolean exist, String cause) {
        this.exist = exist;
        this.cause = cause;
    }

    public boolean isExist() {
        return exist;
    }

    public String getCause() {
        return cause;
    }
}
