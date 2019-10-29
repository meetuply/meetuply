package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;

import java.util.List;

@Component
public class AchievementService {

    @Autowired
    AchievementDAO achievementDAO;

    public Achievement get(Integer id){
        return achievementDAO.get(id);
    }

    public void create(Achievement achievement){
        achievementDAO.save(achievement);
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
}
