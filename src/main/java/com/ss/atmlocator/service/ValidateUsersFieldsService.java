package com.ss.atmlocator.service;

import com.ss.atmlocator.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vasyl Danylyuk on 22.11.2014.
 */
@Service
public class ValidateUsersFieldsService implements Validator {

    //Property with validation RegExps
    //private final String PROPERTY_PATH = "resources\\validation.properties";
    private Properties validationPatterns;



    public ValidateUsersFieldsService() {
        /*try {
            validationPatterns.load(new FileInputStream(PROPERTY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        validationPatterns = new Properties();
        validationPatterns.setProperty("email", "^\\w[\\w-]*(\\.(\\w[\\w-]*)+)*@([a-zA-Z0-9]" +
                                                "([-a-zA-Z0-9]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z][a-zA-Z]+$");
        validationPatterns.setProperty("login", "^(\\w){4,}$");
        validationPatterns.setProperty("passwordMinLength", "6");
        validationPatterns.setProperty("passwordRequiredChar1", ".*([a-z]|[а-я])+.*");
        validationPatterns.setProperty("passwordRequiredChar2", ".*([A-Z]|[Є-Я])+.*");
        validationPatterns.setProperty("passwordRequiredChar3", ".*[0-9]+.*");
    }

    @Override
    public boolean supports(Class<?> Clazz) {
        return User.class.isAssignableFrom(Clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {

        if(! validateLogin(((User)object).getLogin())){
            errors.rejectValue("login", ValidationResult.INVALID_LOGIN.toString());
        };

        if(! validateEmail(((User)object).getEmail())){
            errors.rejectValue("email", ValidationResult.INVALID_EMAIL.toString());
        };

        if(! validatePassword(((User)object).getPassword())){
            errors.rejectValue("password", ValidationResult.INVALID_PASSWORD.toString());
        };
    }



    enum ValidationResult{
        INVALID_LOGIN,
        INVALID_EMAIL,
        INVALID_PASSWORD
    }


    private boolean validateEmail(String email){
        Pattern emailPattern = Pattern.compile(validationPatterns.getProperty("email"));
        return emailPattern.matcher(email).matches();
    }
    private boolean validateLogin(String login){
        Pattern emailPattern = Pattern.compile(validationPatterns.getProperty("login"));
        return emailPattern.matcher(login).matches();
    }
    private boolean validatePassword(String password){
        if(password.length() >= Integer.parseInt(validationPatterns.getProperty("passwordMinLength"))){//checking password length
            Pattern requiredChar1 = Pattern.compile(validationPatterns.getProperty("passwordRequiredChar1"));
            if(requiredChar1.matcher(password).matches()){//checking exiting required character 1
                Pattern requiredChar2 = Pattern.compile(validationPatterns.getProperty("passwordRequiredChar2"));
                if(requiredChar2.matcher(password).matches()){//checking exiting required character 2
                    Pattern requiredChar3 = Pattern.compile(validationPatterns.getProperty("passwordRequiredChar3"));
                    if(requiredChar3.matcher(password).matches()){//checking exiting required character 3
                        return true;//if password is valid
                    }else {return false;}//if password don't have require character 3
                }else {return false;}//if password don't have require character 2
            }else {return false;}//if password don't have require character 1
        }else {return false;}//if password too short
    }
}
