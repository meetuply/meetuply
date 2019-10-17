package ua.meetuply.backend.service;

import java.io.IOException;

public interface EmailService {

    void sendGreetingEmail(String receiver, String templateName, String subject);
    void sendVerificationEmail(String receiver, String templateName, String subject, String verificationCode);

}
