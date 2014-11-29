package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;
import freemarker.cache.StringTemplateLoader;
import org.springframework.core.io.ClassPathResource;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;


/**
 * Class for creating e-mail messages from templates
 */
public class EmailCreator {
    private String dirPath;

    public EmailCreator(String path) throws IOException {
        dirPath = new ClassPathResource(path).getURI().getPath();
    }

    //Method for creating e-mail message to user if password was changed
    public String toUser(User user, String newPassword) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dirPath.concat("to_user.st")));
        String temp = "";
        String line;
        while ((line = reader.readLine()) != null)
        temp += line+"\n";
        ST template = new ST(temp, '<', '>');
        //ST template = templates.getInstanceOf("to_user");
        template.add("name", user.getLogin());
        template.add("password", user.getPassword());
        return template.render();
    }

    //Method for creating e-mail message to user if password wasn't changed
    public String toUser(User user){
        StringBuilder message = new StringBuilder();
        message.append("Dear, "+user.getLogin()+"! \n");
        message.append("We are pleased that You are using ATM_locator. \n");
        message.append("It's your credential for login to ATM_locator: \n");
        message.append("\t login : "+user.getLogin()+" \n");
        message.append("\t password : "+user.getPassword()+" \n");
        return message.toString();
    }
}
