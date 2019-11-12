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
    private static final String TEMPLATES_PATH = "/templates/";

    private static final String DEACTIVATION_TEMPLATE_NAME = "diactivation-email.ftl";
    private static final String RECOVER_TEMPLATE_NAME = "recover-email.ftl";
    private static final String SUCCESS_RECOVER_TEMPLATE_NAME = "success-recover-email.ftl";
    private static final String CANCELLATION_TEMPLATE_NAME = "cancellation-email.ftl";
    private static final String GREETING_TEMPLATE_NAME = "email-template.ftl";
    private static final String VERIFICATION_TEMPLATE_NAME = "verification-email.ftl";

    @Value("${spring.mail.username}")
    private String sender;

    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private Configuration getFreeMarkerConfiguration;

    @Override
    public void sendGreetingEmail(AppUser receiver) {
        Mail mail = prepareMail(receiver.getEmail(), "Greeting");

        String hostName = System.getenv("HOST_NAME");
        if (hostName == null) hostName = "localhost:4200";

        Map<String, Object> model = new HashMap<>();
        model.put("login_url", "http://" + hostName + "/#/login");
        model.put("name", receiver.getFullName());
        mail.setModel(model);
        sendEmail(mail, GREETING_TEMPLATE_NAME);
    }

    @Override
    public void sendVerificationEmail(AppUser receiver, String subject, String verificationCode) {
        Mail verificationMail = prepareMail(receiver.getEmail(), subject);
        Map<String, Object> model = new HashMap<>();
        model.put("VERIFICATION_URL", verificationCode);
        model.put("name", receiver.getFirstName());
        verificationMail.setModel(model);
        sendEmail(verificationMail, VERIFICATION_TEMPLATE_NAME);
    }

    @Override
    public void sendDeactivatinEmail(AppUser receiver) {
        Mail mail = prepareMail(receiver.getEmail(), "Your account was deactivated");
        Map<String, Object> model = new HashMap<>();
        model.put("name", receiver.getFullName());
        mail.setModel(model);
        sendEmail(mail, DEACTIVATION_TEMPLATE_NAME);
    }

    @Override
    public void sendRecoverEmail(AppUser receiver, String recoveryURL) {
        Mail recoveryMail = prepareMail(receiver.getEmail(), "Recovery email");
        Map<String, Object> model = new HashMap<>();
        model.put("VERIFICATION_URL", recoveryURL);
        model.put("name", receiver.getFirstName());
        recoveryMail.setModel(model);
        sendEmail(recoveryMail, RECOVER_TEMPLATE_NAME);

    }

    @Override
    public void sendSuccessRecoverEmail(AppUser receiver) {
        Mail informMail = prepareMail(receiver.getEmail(), "Recovery email");
        Map<String, Object> model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        informMail.setModel(model);
        sendEmail(informMail, SUCCESS_RECOVER_TEMPLATE_NAME);
    }

    @Override
    public void informCancellation(AppUser receiver, Meetup meetup) {
        Mail informMail = prepareMail(receiver.getEmail(), "'" + meetup.getMeetupTitle() + "'was cancel");
        Map<String, Object> model = new HashMap<>();
        model.put("name", receiver.getFirstName());
        model.put("meetup_name", meetup.getMeetupTitle());
        informMail.setModel(model);
        sendEmail(informMail, CANCELLATION_TEMPLATE_NAME);
    }

    @Async
    @Override
    public void sendEmail(Mail mail, String template) {
        MimeMessagePreparator messagePreparator = mimeMessage -> prepareMimeMessage(template, mail, mimeMessage);
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