package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;
import com.ss.atmlocator.entity.UserStatus;
import org.springframework.core.io.ClassPathResource;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.mail.MessagingException;

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
            stGroup.encoding = "UTF-8";
        }catch (IOException ioe){
            logger.fatal("Can't load StringTemplateGroup from dir " + dirPath);
        }

    }

    //Method for creating e-mail message to user if password was changed
    public String toUser(User user) throws MessagingException {
        String templateName = user.getEnabled() == UserStatus.ENABLED ? Constants.UPDATE_TEMPLATE_ENABLED_USER : Constants.UPDATE_TEMPLATE_DISABLED_USER;

        logger.info("Loading email message template from file" + templateName + ".st");
        ST template = stGroup.getInstanceOf(templateName);
        if(template == null){
            logger.error("Can't load email from file "+templateName+".st");
            throw new MessagingException("Can't load email from file "+templateName+".st");
        }
        template.add(Constants.USER_NAME, user.getName());
        template.add(Constants.USER_LOGIN, user.getLogin());
        template.add(Constants.USER_EMAIL, user.getEmail());
        template.add(Constants.USER_PASSWORD, user.getPassword() != null ? user.getPassword() : "Password didn't change");
        String s = template.render();
        return s;
    }

}
