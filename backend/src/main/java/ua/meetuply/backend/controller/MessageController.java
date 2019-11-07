package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.meetuply.backend.model.Message;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.model.SocketNotification;
import ua.meetuply.backend.service.ChatService;
import ua.meetuply.backend.service.NotificationService;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    NotificationService notififcationService;

    @MessageMapping("/in")
    public void greeting(@Payload Message message) throws Exception {
        chatService.addMessage(message);
        template.convertAndSend("/chat/session_messages/" + message.getTo_room_id(), message);


        // System.out.println(message.getFr); //instead of getFrom, get room, and the get secons user
        //template.convertAndSend("/notifications/new/" + message.getFrom(), "New message, check inbox!");

        SocketNotification n = new SocketNotification();
        n.setUid(1);
        n.setDateTime(LocalDateTime.now());
        n.setIsRead(false);
        n.setReceiverId(101);
        n.setHtmlText("<p>-</p>");
        n.setPlainText("New message income: " + message.getContent());

        System.out.println(message.getFrom());
        System.out.println(n.getReceiverId());


        Notification n2 = new Notification();
        n2.setNotificationID(1);
        n2.setDate_time(LocalDateTime.now());
        n2.setIsRead(0);
        n2.setReceiverID(101);
        n2.setTemplateID(1);
        notififcationService.saveNotification(n2);


        notififcationService.sendNotification(n, message.getFrom());
    }

}
