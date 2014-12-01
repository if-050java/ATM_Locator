package com.ss.atmlocator.utils;




import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import static com.ss.atmlocator.utils.ExceptionParser.parseExceptions;



public class SendMails {

    final Logger logger = Logger.getLogger(SendMails.class.getName());

    private MailSender mailSender;
    private String from;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String msg) throws MailException  {

        try{
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);

        mailSender.send(message);

        }
        catch (MailException exp){
            logger.error(parseExceptions(exp));
            throw exp;
        }
    }

}
