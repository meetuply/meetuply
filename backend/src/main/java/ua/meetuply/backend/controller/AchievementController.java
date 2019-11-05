package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.dao.AchievementDAO;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.service.AchievementService;

import java.util.List;

@RequestMapping("api/achievements")
@Transactional
@RestController
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AchievementDAO achievementDAO;

    @GetMapping()
    public @ResponseBody
    Iterable<Achievement> getAll() {
        return achievementService.getAll();
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody
    Iterable<Achievement> getUserAchievements(@PathVariable("userId") Integer userId) {
        return achievementService.getUserAchievements(userId);
    }

    @GetMapping("/{achievementId}")
    public Achievement get(@PathVariable("achievementId") Integer achievementId) {
        return achievementService.get(achievementId);
    }

    @PostMapping()
    public Achievement create(@RequestBody Achievement achievement){
        return achievementService.saveReturn(achievement);
    }

    @GetMapping("/sum/{userId}")
    public Integer getUserAchievementsSum(@PathVariable("userId") Integer userId){
        return achievementService.getUserAchievementsSum(userId);
    }

    @PostMapping("/meetupsTopic/same")
    public void createForMeetupsSameQuantity(@RequestParam String achievementId,
                                 @RequestParam String[] topics,
                                 @RequestParam String quantity){
        Integer achievementIdInteger = Integer.valueOf(achievementId);
        Integer quantityInteger = Integer.valueOf(quantity);
        achievementService.createForMeetupsSameQuantity(achievementIdInteger, topics, quantityInteger);
    }

    @PutMapping("/{achievementId}")
    public ResponseEntity updateAchievement(@PathVariable("achievementId") Integer achievementId, @RequestBody Achievement achievement) {
        if (achievementService.get(achievementId) == null) {
            return ResponseEntity.badRequest().build();
        }
        achievement.setAchievementId(achievementId);
        achievementService.update(achievement);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity deleteAchievement(@PathVariable("achievementId") String achievementId){
        Integer idInteger = Integer.valueOf(achievementId);
        if (achievementService.get(idInteger) == null) {
            return ResponseEntity.badRequest().build();
        }
        achievementService.delete(idInteger);
        return ResponseEntity.ok().build();
    }
}