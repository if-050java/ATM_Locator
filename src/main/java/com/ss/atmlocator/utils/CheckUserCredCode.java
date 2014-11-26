package com.ss.atmlocator.utils;

public class CheckUserCredCode {

    private boolean canUse;
    private String cause;

    public CheckUserCredCode(boolean canUse, String cause) {
        this.canUse = canUse;
        this.cause = cause;
    }

    public boolean getCanUse() {
        return canUse;
    }

    public String getCause() {
        return cause;
    }
}
