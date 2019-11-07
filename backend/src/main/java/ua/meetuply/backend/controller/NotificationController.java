package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.NotificationService;

import java.util.List;
import java.util.Map;

@RequestMapping("api/notifications")
@Transactional
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AppUserService appUserService;




    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable("id") Integer id) {
        if (notificationService.get(id) == null) { ResponseEntity.badRequest().build();}
        else { notificationService.delete(id); }
    }


    @GetMapping("/{id}")
    public @ResponseBody
    List<Map<String, Object>> getAllCurrentNotification(@PathVariable("id") Integer id) {
        return notificationService.getCurrentUserNotification(appUserService.getCurrentUserID(), id);
    }

}