package com.ss.atmlocator.service;

import com.ss.atmlocator.utils.SendMails;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * This class can sent some message to admin email;
 */
@Service
public class FeedBackService {
    final static org.slf4j.Logger log = LoggerFactory.getLogger(FeedBackService.class);
    private static String email = "www.hroback-admin@gmail.com";// admin email
    private static String subject = "user feedback";
    @Autowired
    SendMails sendMails;
    public void sentFeedbackTuAdminEmail(String message) {
        try {
            sendMails.sendMail(email ,subject , message);
            log.info("[ADMIN MESSAGE] send from ");
        } catch (MessagingException messageEx) {
            log.error("[ADMIN MESSAGE] exception"+messageEx.getMessage());

        }

    }
}
