package ua.meetuply.backend.mail_notifications.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import ua.meetuply.backend.mail_notifications.entity.Mail;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;
import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;


@Service
public class EmailServiceImpl implements EmailService {

    private static final String ATTACHMENT = "logo.png";

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private FreeMarkerConfigurer freemarkerConfigurer;

    @Override
    public void sendSimpleMessage(Mail mail, String templateName, String attachmentResource)
            throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessageHelper.addAttachment(ATTACHMENT, new ClassPathResource(attachmentResource));

        Template template = freemarkerConfigurer.getConfiguration().getTemplate(templateName);
        String html = processTemplateIntoString(template, mail.getModel());

        mimeMessageHelper.setTo(mail.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setFrom(mail.getFrom());

        mailSender.send(message);
    }

}