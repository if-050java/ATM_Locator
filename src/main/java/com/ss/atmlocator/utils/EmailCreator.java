package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;
import org.springframework.core.io.ClassPathResource;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STRawGroupDir;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Class for creating e-mail messages from templates
 */
public class EmailCreator {

    private final Logger logger = Logger.getLogger(EmailCreator.class.getName());

    private String dirPath;
    private STGroup stGroup;

    private final char TEMPLATE_DELIMITER = '#';

    public EmailCreator(String path){
        //creating template group  from files in dir
        try {
            dirPath = new ClassPathResource(path).getURI().getPath();
            logger.info("Loading StringTemplateGroup from dir "+dirPath);
            stGroup = new STRawGroupDir(dirPath, TEMPLATE_DELIMITER, TEMPLATE_DELIMITER);
        }catch (IOException ioe){
            logger.fatal("Can't load StringTemplateGroup from dir " + dirPath);
        }

    }

    //Method for creating e-mail message to user if password was changed
    public String create(String templateName, User user) {
        ST template = stGroup.getInstanceOf(templateName);
        if(template == null){
            logger.error("Can't load StringTemplate from file "+templateName+".st");
            return null;
        }
        template.add(Constants.USER_LOGIN, user.getLogin());
        template.add(Constants.USER_EMAIL, user.getEmail());
        template.add(Constants.USER_PASSWORD, user.getPassword() != null ? user.getPassword() : "Password didn't change.");
        return template.render();
    }
}
