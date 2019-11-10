package ua.meetuply.backend.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Mail;
import ua.meetuply.backend.model.Meetup;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
@EnableAsync
public class EmailServiceImpl implements EmailService {

    private static final String UTF_8 = "UTF-8";
    private static final String NAME = "name";
    private static final String TEMPLATES_PATH = "/templates/";

    private static final String DEACTIVATION_TEMPLATE_NAME = "diactivation-email.ftl";
    private static final String RECOVER_TEMPLATE_NAME = "recover-email.ftl";
    private static final String SUCCESS_RECOVER_TEMPLATE_NAME = "success-recover-email.ftl";
    private static final String CANCELLATION_TEMPLATE_NAME = "cancellation-email.ftl";

    @Value("${spring.mail.username}")
    private String sender;

    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private Configuration getFreeMarkerConfiguration;

    @Override
    @Async
    public void sendGreetingEmail(AppUser receiver, String templateName, String subject) {
        Mail mail = prepareMail(receiver.getEmail(), subject);

        String hostName = System.getenv("HOST_NAME");
        if (hostName == null) hostName = "localhost:4200";

        String loginUrl = "http://" + hostName + "/#/login";
        Map<String, Object> model = new HashMap<>();
        model.put("login_url", loginUrl);
        model.put("name", receiver.getFullName());
        mail.setModel(model);

        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(templateName, mail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }
    }

    @Async
    @Override
    public void sendVerificationEmail(AppUser receiver, String templateName, String subject, String verificationCode) {
        Mail verificationMail = new Mail();
        verificationMail.setMailFrom(sender);
        verificationMail.setMailTo(receiver.getEmail());
        verificationMail.setMailSubject(subject);
        Map<String, Object> model = new HashMap<>();
        model.put("VERIFICATION_URL", verificationCode);
        model.put("name", receiver.getFirstName());
        verificationMail.setModel(model);
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(templateName, verificationMail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }

    }

    @Async
    @Override
    public void sendDeactivatinEmail(AppUser receiver) {
        Mail mail = prepareMail(receiver.getEmail(), "Your account was deactivated");

        Map<String, Object> model = new HashMap<>();
        model.put("name", receiver.getFullName());
        mail.setModel(model);
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(DEACTIVATION_TEMPLATE_NAME, mail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }
    }

    @Override
    public void sendRecoverEmail(AppUser receiver, String recoveryURL) {
        Mail recoveryMail = new Mail();
        recoveryMail.setMailFrom(sender);
        recoveryMail.setMailTo(receiver.getEmail());
        recoveryMail.setMailSubject("Recovery email");
        Map<String, Object> model = new HashMap<>();
        model.put("VERIFICATION_URL", recoveryURL);
        model.put("name", receiver.getFirstName());
        recoveryMail.setModel(model);
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(RECOVER_TEMPLATE_NAME, recoveryMail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }

    }

    @Override
    public void sendSuccessRecoverEmail(AppUser receiver) {
        Mail recoveryMail = new Mail();
        recoveryMail.setMailFrom(sender);
        recoveryMail.setMailTo(receiver.getEmail());
        recoveryMail.setMailSubject("Recovery email");
        Map<String, Object> model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        recoveryMail.setModel(model);
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(SUCCESS_RECOVER_TEMPLATE_NAME, recoveryMail, mimeMessage);

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailSendException(e.getMessage());
        }
    }

    public void informCancellation(AppUser user, Meetup meetup) {
        Mail recoveryMail = new Mail();
        recoveryMail.setMailFrom(sender);
        recoveryMail.setMailTo(user.getEmail());
        recoveryMail.setMailSubject("'" + meetup.getMeetupTitle() + "'was cancel");
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getFirstName());
        model.put("meetup_name", meetup.getMeetupTitle());
        recoveryMail.setModel(model);
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(CANCELLATION_TEMPLATE_NAME, recoveryMail, mimeMessage);

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
        return mail;
    }

}