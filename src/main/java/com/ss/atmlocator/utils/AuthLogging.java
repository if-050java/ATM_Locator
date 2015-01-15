package com.ss.atmlocator.utils;

import com.ss.atmlocator.dao.IUsersDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import static com.ss.atmlocator.utils.ExceptionParser.parseExceptions;

import java.util.Calendar;

/**
 * Application Event listener Class for log in event
 */


public class AuthLogging implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private IUsersDAO usersDAO;



    final Logger logger = Logger.getLogger(AuthLogging.class.getName());

        @Override
        public void onApplicationEvent(final AuthenticationSuccessEvent event) {

            UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
            try{
                usersDAO.writeLoginTime(userDetails.getUsername());
            }
            catch (Exception exp){
                logger.info(parseExceptions(exp));
            }
        }

}


