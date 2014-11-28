package com.ss.atmlocator.utils;

/**
 * Created by us8610 on 11/25/2014.
 */

import java.util.List;

public class ValidationOutMessage {

    private String status;
    private List<ValidateUserFields> validateUserFieldsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ValidateUserFields> getValidateUserFieldsList() {
        return this.validateUserFieldsList;
    }

    public void setValidateUserFieldsList(List<ValidateUserFields> validateUserFieldsList) {
        this.validateUserFieldsList = validateUserFieldsList;
    }
}