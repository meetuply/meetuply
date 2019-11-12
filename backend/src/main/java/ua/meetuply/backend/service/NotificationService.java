package ua.meetuply.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ua.meetuply.backend.dao.NotificationsDAO;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.model.NotificationTemplate;
import ua.meetuply.backend.model.SocketNotification;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class NotificationService {


    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @Autowired
    private NotificationService notificationService;

    //websocket send
    private void sendNotification(SocketNotification notification, Integer recipient) {


        template.convertAndSend("/notifications/new/" + recipient, notification);

    }

    public void sendNotification(Integer receiver, String template) {


        Notification n2 = new Notification();
        n2.setNotificationId(1);
        n2.setDateTime(LocalDateTime.now());
        n2.setIsRead(false);
        n2.setReceiverId(receiver);
        n2.setTemplateId(notificationTemplateService.getByName(template).getNotificationTemplateId());
        notificationService.saveNotification(n2);
        SocketNotification n = new SocketNotification(n2, notificationTemplateService.get(n2.getTemplateId()));

        sendNotification(n, receiver);

    }

    //========================s

    @Autowired
    private NotificationsDAO notificationsDAO;

    public SocketNotification get(Integer id) {
        return notificationsDAO.get(id);
    }

    public List<SocketNotification> getAll() {
        return notificationsDAO.getAll();
    }

    public void saveNotification(Notification notification) {
        notificationsDAO.save(notification);
    }

    public void update(Notification notification) {
        notificationsDAO.update(notification);
    }

    public void delete(Integer id) {
        notificationsDAO.delete(id);
    }


    public List<SocketNotification> getUserNotifications(Integer userID) {
        return notificationsDAO.getUserNotifications(userID);
    }

    public List<SocketNotification> getUserNotificationsByStatus(Integer userID, boolean isRead) {
        return notificationsDAO.getReadedOrUnreadedNotifications(userID, isRead);
    }

    /*
    public void updateNotificetionRead(Integer id) {
        notificationsDAO.setRead(id);
    }*/
}
