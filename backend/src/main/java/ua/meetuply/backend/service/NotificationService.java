package ua.meetuply.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.meetuply.backend.dao.NotificationsDAO;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.model.SocketNotification;

import java.util.List;
import java.util.Map;

@Controller
public class NotificationService {


    @Autowired
    private SimpMessagingTemplate template;

    //websocket send
    public void sendNotification(SocketNotification notification, Integer recipient) {

        template.convertAndSend("/notifications/new/" + recipient, notification);

    }

    //========================s

    @Autowired
    private NotificationsDAO notificationsDAO;

    public Notification get(Integer id) {
        return notificationsDAO.get(id);
    }

    public List<Notification> getAll() {
        List<Notification> allNotifications = notificationsDAO.getAll();
        if (allNotifications != null) {
            System.out.println(allNotifications.toString());
            return allNotifications;
        } else return null;

    }

    public void saveNotification(Notification notification) {
        notificationsDAO.save(notification);
    }

    public void update(Notification notification) {
        notificationsDAO.update(notification);
    }

    public void updateNotificetionRead(Notification notification) {
        notification.setIsRead(1);
        notificationsDAO.update(notification);
    }

    public void delete(Integer id) {
        notificationsDAO.delete(id);
    }

    public List<Map<String, Object>> getAllUserNotifications(Integer userID) {
        List<Map<String, Object>> allUserNotifications = notificationsDAO.getAllUserNotifications(userID);
        if (allUserNotifications != null) {
            System.out.println(allUserNotifications.toString());

            //List<Map<String, Object>> list = getMyMap();
            for (Map<String, Object> map : allUserNotifications) {
                System.out.println("--next list--");
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.println("key: " + entry.getKey() + " - " + "value: " + entry.getValue());
                }
            }

            return allUserNotifications;
        } else return null;
    }

    public List<Map<String, Object>> getCurrentUserNotification(Integer userId, Integer notificationId) {
        return notificationsDAO.getCurrentUserNotification(userId, notificationId);
    }

    public List<Map<String, Object>> getReadedOrUnreadedNotifications(Integer userId, Integer readedOrUnreaded) {
        return notificationsDAO.getReadedOrUnreadedNotifications(userId, readedOrUnreaded);
    }


}
