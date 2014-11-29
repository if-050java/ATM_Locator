package com.ss.atmlocator.utils;

import com.ss.atmlocator.entity.User;
import freemarker.cache.StringTemplateLoader;
import org.springframework.core.io.ClassPathResource;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;


/**
 * Class for creating e-mail messages from templates
 */
public class EmailCreator {
    private STGroup templates;

    public EmailCreator(String path) {
        String folderPath = new ClassPathResource(path).getFilename();
        templates = new STGroupDir(folderPath);
    }

    //Method for creating e-mail message to user if password was changed
    public String toUser(User user, String newPassword){
        ST template = templates.getInstanceOf("to_user");
        template.add("name", user.getLogin());
        template.add("password", user.getPassword());
        return template.toString();
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
