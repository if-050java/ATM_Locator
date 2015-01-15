package com.ss.atmlocator.entity;

import org.codehaus.jackson.annotate.JsonValue;

public enum UserStatus{
    DISABLED (0),
    ENABLED (1);

    private final int value;

    UserStatus(int value){
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

}