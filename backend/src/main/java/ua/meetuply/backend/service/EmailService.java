package ua.meetuply.backend.service;

public interface EmailService {

    void sendEmail(String receiver, String templateName, String subject);

}
