package ua.meetuply.backend.mail_notifications.service;

import freemarker.template.TemplateException;
import ua.meetuply.backend.mail_notifications.entity.Mail;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendSimpleMessage(Mail mail, String templateName, String attachmentResource)
            throws MessagingException, IOException, TemplateException;

}
