package ua.meetuply.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.model.AchievementType;

import javax.xml.transform.sax.SAXSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
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
        log.debug("Saving achievement");
        achievementDAO.save(achievement);
    }

    public Achievement saveReturn(Achievement achievement) {
        log.debug("Saving achievement and returning achievement id");
        return achievementDAO.get(achievementDAO.saveReturnId(achievement));
    }

    public void createForMeetupsSameQuantity(Integer achievementId, String[] topics, Integer quantity) {
        log.debug("Saving achievement for meetups with topics");
        Arrays.stream(topics).forEach(topic -> {
            Integer topicId = topicService.getIdByName(topic);
            achievementDAO.saveAchievementMeetup(achievementId, topicId, quantity);
        });
    }

    public void update(Achievement achievement) throws NotFoundException {
        log.debug("Updating achievement");
        if (achievementDAO.get(achievement.getAchievementId())==null){
            throw new NotFoundException("Cannot find achievement #" + achievement.getAchievementId());
        }
        achievementDAO.update(achievement);
    }

    public void delete(Integer id) throws NotFoundException {
        log.debug("Deleting achievement");
        if (achievementDAO.get(id)==null){
            throw new NotFoundException("Cannot find achievement #" + id);
        }
        achievementDAO.delete(id);
    }

    public List<Achievement> getAll() {
        log.debug("Getting all achievements");
        return achievementDAO.getAll();
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        log.debug("Getting user achievements list");
        return achievementDAO.getUserAchievements(userId);
    }

    @Transactional
    void checkOne(AchievementType type){
        log.debug("Checking if user should get an achievement");
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
            System.out.println(achievementsIdlist.get(0));
            for (Integer achievementId: achievementsIdlist){
                awardOne(achievementId, currentUserId);
            }
        }
    }

    private void awardOne(Integer achievementId, Integer userId) {
        log.debug("Awarding an achievement");
        achievementDAO.updateAchievementUser(achievementId, userId);
    }

    public Integer getUserAchievementsSum(Integer userId) {
        log.debug("Getting user achievements sum");
        return achievementDAO.getUserAchievementsSum(userId);
    }
}
