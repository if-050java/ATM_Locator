package com.ss.atmlocator.utils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;

/**
 * Application Event listener Class for log in event
 */
public class AuthLogging implements ApplicationListener<AuthenticationSuccessEvent> {

    final Logger logger = Logger.getLogger(AuthLogging.class.getName());

        @Override
        public void onApplicationEvent(final AuthenticationSuccessEvent event) {

            UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            logger.info("user log in, user name: " + userDetails.getUsername()+"; current timestamp: "+currentTimestamp);
        }

}


