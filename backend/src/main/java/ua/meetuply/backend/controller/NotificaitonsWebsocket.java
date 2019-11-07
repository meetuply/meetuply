package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificaitonsWebsocket {


    //@Autowired
    //private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate template;

}
