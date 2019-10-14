package ua.meetuply.backend.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.meetuply.backend.model.Mail;
import freemarker.template.Configuration;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private static final String UTF_8 = "UTF-8";
    private static final String NAME = "name";
    private static final String TEMPLATES_PATH = "/templates/";

    @Value("${spring.mail.username}")
    private String sender;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private Configuration getFreeMarkerConfiguration;

    @Override
    public void sendEmail(String receiver, String templateName, String subject) {
        Mail mail = prepareMail(receiver, subject);

        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(templateName, mail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }
    }

    private void prepareMimeMessage(String templateName, Mail mail, MimeMessage mimeMessage)
            throws IOException, TemplateException, MessagingException {

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, UTF_8);

        getFreeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), TEMPLATES_PATH);
        Template template = getFreeMarkerConfiguration.getTemplate(templateName, UTF_8);
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

        helper.setFrom(mail.getMailFrom());
        helper.setTo(mail.getMailTo());
        helper.setText(text, true);
        helper.setSubject(mail.getMailSubject());
    }

    private Mail prepareMail(String email, String subject) {
        Mail mail = new Mail();
        mail.setMailFrom(sender);
        mail.setMailTo(email);
        mail.setMailSubject(subject);

        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put(NAME, mail.getMailTo());
        mail.setModel(mailModel);
        return mail;
    }

}