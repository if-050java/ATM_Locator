package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;

import java.io.File;

/**
 * Created by Vasyl Danylyuk on 27.11.2014.
 */
public class EmailCreator {
    public static String create(User user){
        StringBuilder message = new StringBuilder();
        message.append("Dear, "+user.getLogin()+"! \n");
        message.append("We are pleased that You are using ATM_locator. \n");
        message.append("It's your credential for login to ATM_locator: \n");
        message.append("\t login : "+user.getLogin()+" \n");
        message.append("\t password : "+user.getPassword()+" \n");
        return message.toString();
    }
}
