package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Message;
import ua.meetuply.backend.service.ChatService;

@RequestMapping("api/chat")
@RestController
public class ChatController {


    @Autowired
    private ChatService chatService;

    @GetMapping("/rooms")
    public @ResponseBody
    Iterable<Integer> getRooms() {
        return chatService.getChatRooms();
    }


    //retrieves all emssages in the chat room
    @GetMapping("/rooms/{roomId}/messages")
    public @ResponseBody
    Iterable<Message> getRoomMessages(@PathVariable("roomId") Integer roomId) {
        return chatService.getRoomMessages(roomId);
    }

    @GetMapping("/rooms/{roomId}/members")
    public @ResponseBody
    Iterable<Integer> getRoomMembers(@PathVariable("roomId") Integer roomId) {
        return chatService.getRoomMembers(roomId);
    }


    @GetMapping("/rooms/common/{user1}/{user2}")
    public @ResponseBody
    Integer getCommon(@PathVariable("user1") Integer user1, @PathVariable("user2") Integer user2) {
        return chatService.getCommonRoom(user1, user2);
    }

    @PostMapping("/rooms/common/{user1}/{user2}")
    public @ResponseBody
    Integer createCommon(@PathVariable("user1") Integer user1, @PathVariable("user2") Integer user2) {
        return chatService.createCommonRoom(user1, user2);
    }


    @GetMapping("/rooms/{roomId}/last")
    public @ResponseBody
    Message getRoomLastMessage(@PathVariable("roomId") Integer roomId) {
        return chatService.getLastMessage(roomId);
    }

}
