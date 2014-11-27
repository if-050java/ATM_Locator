package com.ss.atmlocator.utils;


import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SendMails {

    private MailSender mailSender;
    private String from;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);

        mailSender.send(message);

    }

}
