package ua.meetuply.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.service.AchievementService;

@Slf4j
@RequestMapping("api/achievements")
@Transactional
@RestController
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping()
    public @ResponseBody
    Iterable<Achievement> getAll() {
        log.debug("Getting all achievements");
        return achievementService.getAll();
    }

    @GetMapping("/user/{userId}")
    public Iterable<Achievement> getUserAchievements(@PathVariable("userId") Integer userId) {
        log.debug("Getting user achievements");
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/{achievementId}")
    public Achievement get(@PathVariable("achievementId") Integer achievementId) {
        log.debug("Getting achievement by id");
        return achievementService.get(achievementId);
    }

    @PostMapping()
    public Achievement create(@RequestBody Achievement achievement){
        log.debug("Creating achievement");
        return achievementService.saveReturn(achievement);
    }

    @GetMapping("/sum/{userId}")
    public Integer getUserAchievementsSum(@PathVariable("userId") Integer userId){
        log.debug("Getting user achievements sum");
        return achievementService.getUserAchievementsSum(userId);
    }

    @PostMapping("/meetupsTopic/same")
    public void createForMeetupsSameQuantity(@RequestParam String achievementId,
                                 @RequestParam String[] topics,
                                 @RequestParam String quantity){
        log.debug("Creating achievement for meetups with topics");
        Integer achievementIdInteger = Integer.valueOf(achievementId);
        Integer quantityInteger = Integer.valueOf(quantity);
        achievementService.createForMeetupsSameQuantity(achievementIdInteger, topics, quantityInteger);
    }

    @PutMapping("/{achievementId}")
    public ResponseEntity updateAchievement(@PathVariable("achievementId") Integer achievementId,
                                            @RequestBody Achievement achievement) throws NotFoundException {
        log.debug("Updating achievement");
        achievement.setAchievementId(achievementId);
        achievementService.update(achievement);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity deleteAchievement(@PathVariable("achievementId") String achievementId)
            throws NotFoundException {
        log.debug("Deleting achievement");
        Integer idInteger = Integer.valueOf(achievementId);
        achievementService.delete(idInteger);
        return ResponseEntity.ok().build();
    }
}