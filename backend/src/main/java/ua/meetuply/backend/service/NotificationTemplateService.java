package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.NotificationTemplateDAO;
import ua.meetuply.backend.model.NotificationTemplate;

import java.util.List;

@Component
public class NotificationTemplateService {

    @Autowired
    NotificationTemplateDAO notificationTemplateDAO;

    public NotificationTemplate get(Integer id){
        return notificationTemplateDAO.get(id);
    }

    public void create(NotificationTemplate notificationTemplate) {
        notificationTemplateDAO.save(notificationTemplate);
    }

    public void update(NotificationTemplate notificationTemplate) {
        notificationTemplateDAO.update(notificationTemplate);
    }

    public void delete(Integer id){
        notificationTemplateDAO.delete(id);
    }

    public List<NotificationTemplate> getAll(){
        return notificationTemplateDAO.getAll();
    }
}