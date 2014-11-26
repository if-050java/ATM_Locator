package com.ss.atmlocator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Class for transfer to client side response about result of operation deleting/updating user
 */

public class UserControllerResponse {
    private UserControllerResponseStatus status;
    private String message;


    public UserControllerResponseStatus getStatus() {
        return status;
    }

    public UserControllerResponse(UserControllerResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public UserControllerResponse() {
    }

    public void setStatus(UserControllerResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
