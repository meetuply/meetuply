package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.model.AchievementType;

import java.util.ArrayList;
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
        List<Integer> achievementsIdlist = new ArrayList<>();
        switch (type) {
            case FOLLOWERS:
                achievementsIdlist = achievementDAO.getFollowersAchievementId(currentUserId);
                break;
            case POSTS:
                achievementsIdlist = achievementDAO.getPostsAchievementId(currentUserId);
                break;
            case RATING:
                achievementsIdlist = achievementDAO.getRatingAchievementId(currentUserId);
                break;
            case MEETUPS:
                achievementsIdlist = achievementDAO.getMeetupAchievementId(currentUserId);
                break;
            case MEETUPS_TOPIC:
                achievementsIdlist = achievementDAO.getMeetupTopicAchievementId(currentUserId);
                break;
        }
        if (achievementsIdlist.size() != 0){
            for (Integer achievementId: achievementsIdlist){
                awardOne(achievementId, currentUserId);
            }
        }
    }

    private void awardOne(Integer achievementId, Integer userId) {
        achievementDAO.updateAchievementUser(achievementId, userId);
    }

    public Integer getUserAchievementsSum(Integer userId) {
        return achievementDAO.getUserAchievementsSum(userId);
    }
}
