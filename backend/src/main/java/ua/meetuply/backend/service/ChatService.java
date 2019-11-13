package ua.meetuply.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.ChatDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.ChatroomThumbnail;
import ua.meetuply.backend.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class ChatService {


    @Autowired
    ChatDAO chatDAO;

    @Autowired
    AppUserService userService;

    public List<Integer> getChatRooms() {
        return chatDAO.getChatRooms();
    }


    public List<Message> getRoomMessagesChunk(Integer roomId,Integer start,Integer size) {

        return chatDAO.getRoomMessagesChunk(roomId,start,size);

    }

    public List<Integer> getChatRoomsByUser(Integer userId) {
        return chatDAO.getChatRoomsByUser(userId);
    }

    public List<Integer> getRoomMembers(Integer roomId) {
        return chatDAO.getRoomMembers(roomId);
    }


    public List<Message> getRoomMessages(Integer roomId) {
        return chatDAO.getRoomMessages(roomId);
    }

    
    public Message getLastMessage(Integer roomId) {
        return chatDAO.getLastMessage(roomId);
    }

    public void addMessage(Message m) {
        chatDAO.addMessage(m);
    }

    public List<ChatroomThumbnail> getChatRoomsThumbnails(Integer userId) {

        List<ChatroomThumbnail> chats = new ArrayList<>();


        List<Integer> chatRooms = getChatRoomsByUser(userId);

        for (int i = 0; i < chatRooms.size(); ++i) {
            int currentChatroom = chatRooms.get(i);
            ChatroomThumbnail t = new ChatroomThumbnail();
            Message m = getLastMessage(currentChatroom);
            List<Integer> users = getRoomMembers(currentChatroom);
            users.remove(userId);
            AppUser otherUser = userService.getUser(users.get(0));

            t.setMessage(m);
            t.setUser(otherUser);
            t.setRoomId(currentChatroom);
            chats.add(t);
        }

        return chats;
    }

    public Integer getCommonRoom(Integer user1, Integer user2) {


        List<Integer> usr1 = getChatRoomsByUser(user1);
        List<Integer> usr2 = getChatRoomsByUser(user2);
        usr1.retainAll(usr2);
        return usr1.size() <= 0 ? -1 : usr1.get(0);
    }

    public Integer createCommonRoom(Integer user1, Integer user2) {
        return chatDAO.createCommonRoom(user1,user2);
    }
}
