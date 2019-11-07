package ua.meetuply.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.meetuply.backend.model.Notification;

@Controller
public class NotificationService {


    @Autowired
    private SimpMessagingTemplate template;

    public void sendNotification(Notification notification, Integer recipient) {

        template.convertAndSend("/notifications/new/" + recipient, notification);

    }

}
