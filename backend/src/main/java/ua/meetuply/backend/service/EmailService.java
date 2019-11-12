package ua.meetuply.backend.service;

import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Mail;
import ua.meetuply.backend.model.Meetup;

public interface EmailService {

    void sendGreetingEmail(AppUser receiver);
    void sendVerificationEmail(AppUser receiver, String subject, String verificationCode);
    void sendDeactivatinEmail(AppUser receiver);

    void sendRecoverEmail(AppUser user, String recoveryMail);
    void sendSuccessRecoverEmail(AppUser user);
    void informCancellation(AppUser user, Meetup meetup);

    void sendEmail(Mail mail, String template);
}
