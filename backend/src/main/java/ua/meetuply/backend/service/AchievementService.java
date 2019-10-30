package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.model.Topic;

import java.util.Arrays;
import java.util.List;

@Component
public class AchievementService {

    @Autowired
    AchievementDAO achievementDAO;

    @Autowired
    TopicService topicService;

    public Achievement get(Integer id){
        return achievementDAO.get(id);
    }

    public void create(Achievement achievement){
        achievementDAO.save(achievement);
    }

    public Integer saveReturnId(Achievement achievement){
        return achievementDAO.saveReturnId(achievement);
    }

    public void createForMeetupsSameQuantity(Integer achievementId, String[] topics, Integer quantity){
        Arrays.stream(topics).forEach(topic -> {
            Integer topicId = topicService.getIdByName(topic);
            achievementDAO.saveAchievementMeetup(achievementId, topicId, quantity);
        });
    }

    public void update(Achievement achievement){
        achievementDAO.update(achievement);
    }

    public void delete(Integer id){
        achievementDAO.delete(id);
    }

    public List<Achievement> getAll(){
        return achievementDAO.getAll();
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        return achievementDAO.getUserAchievements(userId);
    }
}
