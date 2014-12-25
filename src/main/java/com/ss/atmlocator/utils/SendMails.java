package com.ss.atmlocator.utils;


import org.apache.log4j.Logger;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class SendMails {

    private final Logger logger = Logger.getLogger(SendMails.class.getName());

    private JavaMailSender mailSender;
    private String from;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String msg) throws MessagingException, MailSendException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setText(msg, "UTF-8");
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            mailSender.send(message);

        } catch (MailSendException exp) {
            logger.error(exp.getMessage(), exp);
            throw exp;
        } catch (MessagingException exp) {
            logger.error(exp.getMessage(), exp);
            throw exp;
        }

    }

}
