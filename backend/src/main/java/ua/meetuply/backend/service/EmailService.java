package ua.meetuply.backend.service;

import ua.meetuply.backend.model.AppUser;

import java.io.IOException;

public interface EmailService {

    void sendGreetingEmail(AppUser receiver, String templateName, String subject);
    void sendVerificationEmail(AppUser receiver, String templateName, String subject, String verificationCode);

}
