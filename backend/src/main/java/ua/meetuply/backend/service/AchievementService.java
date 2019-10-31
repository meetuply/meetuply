package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;

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

//    public List<Map<String, Object>> test(Integer userId){
//        return achievementDAO.test(userId);
//    }

    public Achievement get(Integer id) {
        return achievementDAO.get(id);
    }

    public void create(Achievement achievement) {
        achievementDAO.save(achievement);
    }

    public Integer saveReturnId(Achievement achievement) {
        return achievementDAO.saveReturnId(achievement);
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

    public void checkOne(String type){
        Integer currentUserId = appUserService.getCurrentUserID();
        Integer achievementId = null;
        switch (type) {
            case "followers":
                achievementId = achievementDAO.getFollowersAchievementId(currentUserId);
                break;
            case "posts":
                achievementId = achievementDAO.getPostsAchievementId(currentUserId);
                break;
            case "rating":
                achievementId = achievementDAO.getRatingAchievementId(currentUserId);
                break;
            case "meetups":
                achievementId = achievementDAO.getMeetupAchievementId(currentUserId);
                break;
        }
        if (achievementId != null){
            awardAchievement(currentUserId, achievementId);
        }
    }

//    public void checkOneMeetupTopid(Integer topicId){
//        Integer currentUserId = appUserService.getCurrentUserID();
//        List<Map<String, String>> achievementsForUser =
//               achievementDAO.getAchievementIdTopicQuantityForUserSum(currentUserId);
//        Map<String,List<Map<String,Object>>> map=
//                achievementsForUser.stream().collect(Collectors.groupingBy(m-> m.remove("achievement_id")));
//    }

    public void awardAchievement(Integer achievementId, Integer userId) {
        achievementDAO.updateAchievementUser(achievementId, userId);
    }
}
