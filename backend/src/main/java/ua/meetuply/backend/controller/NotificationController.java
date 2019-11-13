package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.dao.NotificationsDAO;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.model.NotificationTemplate;
import ua.meetuply.backend.model.SocketNotification;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.NotificationService;
import ua.meetuply.backend.service.NotificationTemplateService;


import java.util.List;
import java.util.Map;

@RequestMapping("api/notifications")
@Transactional
@RestController
public class NotificationController {

    @Autowired
    private NotificationTemplateService templateService;

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private NotificationsDAO doa;

    @GetMapping("/templates/{id}")
    public @ResponseBody
    NotificationTemplate getAllCurrentNotification(@PathVariable("id") Integer id) {
        return templateService.get(id);
    }

    @GetMapping("/templates")
    public @ResponseBody
    List<NotificationTemplate> getAllCurrentNotification() {
        return templateService.getAll();
    }

    @GetMapping()
    public @ResponseBody
    List<SocketNotification> getAllNotifications() {
        return notificationService.getAll();
    }


    @GetMapping("/{id}")
    public @ResponseBody
    SocketNotification getNotification(@PathVariable("id") Integer id) {
        return notificationService.get(id);
    }




}