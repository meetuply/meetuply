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
import ua.meetuply.backend.service.NotificationTemplateService;


import java.time.LocalDateTime;
import java.util.List;

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

        Integer roomId = message.getTo_room_id();

        List<Integer> users = chatService.getRoomMembers(roomId);
        users.remove(message.getFrom());

        notififcationService.sendNotification(users.get(0),"new_message_template");


    }


}
