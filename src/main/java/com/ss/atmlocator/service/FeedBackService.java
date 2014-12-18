package com.ss.atmlocator.service;

import com.ss.atmlocator.utils.SendMails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by maks on 18.12.2014.
 */
@Service
public class FeedBackService {
    private String email = "www.hroback-admin@gmail.com";
    private String subject = "user feedback";
    @Autowired
    SendMails sendMails;
    public void sentFedbackTuAdminEmail(String message) {
        try {
            sendMails.sendMail(email ,subject , message);
        } catch (MessagingException messageEx) {
            messageEx.printStackTrace();
        }

    }
}
