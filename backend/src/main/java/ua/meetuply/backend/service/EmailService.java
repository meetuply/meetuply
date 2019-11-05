package ua.meetuply.backend.service;

import ua.meetuply.backend.model.AppUser;

public interface EmailService {

    void sendGreetingEmail(AppUser receiver, String templateName, String subject);
    void sendVerificationEmail(AppUser receiver, String templateName, String subject, String verificationCode);
    void sendDeactivatinEmail(AppUser receiver);

    void sendRecoverEmail(AppUser user, String recoveryMail);
    void sendSuccessRecoverEmail(AppUser user);
}
