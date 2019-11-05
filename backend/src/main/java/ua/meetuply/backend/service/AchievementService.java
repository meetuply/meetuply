package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.model.AchievementType;

import java.util.Arrays;
import java.util.List;

@Component
public class AchievementService {

    @Autowired
    AchievementDAO achievementDAO;

    @Autowired
    TopicService topicService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    MeetupService meetupService;

    public Achievement get(Integer id) {
        return achievementDAO.get(id);
    }

    public void create(Achievement achievement) {
        achievementDAO.save(achievement);
    }

    public Achievement saveReturn(Achievement achievement) {
        return achievementDAO.get(achievementDAO.saveReturnId(achievement));
    }

    public void createForMeetupsSameQuantity(Integer achievementId, String[] topics, Integer quantity) {
        Arrays.stream(topics).forEach(topic -> {
            Integer topicId = topicService.getIdByName(topic);
            achievementDAO.saveAchievementMeetup(achievementId, topicId, quantity);
        });
    }

    public void update(Achievement achievement) {
        achievementDAO.update(achievement);
    }

    public void delete(Integer id) {
        achievementDAO.delete(id);
    }

    public List<Achievement> getAll() {
        return achievementDAO.getAll();
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        return achievementDAO.getUserAchievements(userId);
    }

    public void checkOne(AchievementType type){
        Integer currentUserId = appUserService.getCurrentUserID();
        Integer achievementId = null;
        switch (type) {
            case FOLLOWERS:
                achievementId = achievementDAO.getFollowersAchievementId(currentUserId);
                break;
            case POSTS:
                achievementId = achievementDAO.getPostsAchievementId(currentUserId);
                break;
            case RATING:
                achievementId = achievementDAO.getRatingAchievementId(currentUserId);
                break;
            case MEETUPS:
                achievementId = achievementDAO.getMeetupAchievementId(currentUserId);
                break;
        }
        if (achievementId != null){
            awardOne(currentUserId, achievementId);
        }
    }

    public void checkMultiple(){
        Integer currentUserId = appUserService.getCurrentUserID();
        List<Integer> achievementIdList = achievementDAO.getMeetupTopicAchievementId(currentUserId);
        for (Integer achievementId: achievementIdList){
            awardOne(achievementId, currentUserId);
        }
    }

    public void awardOne(Integer achievementId, Integer userId) {
        achievementDAO.updateAchievementUser(achievementId, userId);
    }

    public Integer getUserAchievementsSum(Integer userId) {
        return achievementDAO.getUserAchievementsSum(userId);
    }
}
