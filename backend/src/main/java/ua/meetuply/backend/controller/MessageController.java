package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.meetuply.backend.model.Message;
import ua.meetuply.backend.service.ChatService;

@Controller
public class MessageController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/in")
    public void greeting(@Payload Message message) throws Exception {
        chatService.addMessage(message);
        template.convertAndSend("/chat/session_messages/" + message.getTo_room_id(), message);


       // System.out.println(message.getFr); //instead of getFrom, get room, and the get secons user
        template.convertAndSend("/notifications/new/" + message.getFrom(), "New message, check inbox!");
    }

}
