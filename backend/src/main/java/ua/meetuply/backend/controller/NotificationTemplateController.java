package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.NotificationTemplate;
import ua.meetuply.backend.service.NotificationTemplateService;

import javax.validation.Valid;

@RequestMapping("api/notification_templates")
@Transactional
@RestController
public class NotificationTemplateController {

    @Autowired
    private NotificationTemplateService notificationTemplateService;

    @GetMapping()
    public @ResponseBody
    Iterable<NotificationTemplate> getAllNotificationTemplates() {
        return notificationTemplateService.getAll();
    }

    @GetMapping("/{notificationTemplateId}")
    public NotificationTemplate getOneNotificationTemplate(@PathVariable("notificationTemplateId") Integer notificationTemplateId) {
        return notificationTemplateService.get(notificationTemplateId);
    }

    @PostMapping()
    public ResponseEntity createNewNotificationTemplate(@Valid @RequestBody NotificationTemplate notificationTemplate){
        notificationTemplateService.create(notificationTemplate);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{notificationTemplateId}")
    public ResponseEntity updateNotificationTemplate(@PathVariable("notificationTemplateId") Integer notificationTemplateId, @RequestBody NotificationTemplate notificationTemplate) {
        if (notificationTemplateService.get(notificationTemplateId) == null) {
            return ResponseEntity.notFound().build();
        }
        notificationTemplate.setNotificationTemplateId(notificationTemplateId);
        notificationTemplateService.update(notificationTemplate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationTemplateId}")
    public ResponseEntity deleteNotificationTemplate(@PathVariable("notificationTemplateId") Integer notificationTemplateId){
        if (notificationTemplateService.get(notificationTemplateId) == null) {
            return ResponseEntity.notFound().build();
        }
        notificationTemplateService.delete(notificationTemplateId);
        return ResponseEntity.ok().build();
    }
}