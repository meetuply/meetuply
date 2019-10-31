package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.service.AchievementService;

import java.util.List;
import java.util.Map;

@RequestMapping("api/achievements")
@Transactional
@RestController
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

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

//    @PostMapping("/create")
//    public ResponseEntity<Achievement> create(@RequestBody Achievement achievement){
//        achievementService.create(achievement);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/test/{achievementId}")
//    public List<Map<String, Object>> test(@PathVariable("achievementId") String userId) {
//        Integer id = Integer.valueOf(userId);
//        return achievementService.test(id);
//    }

    @PostMapping("/create")
    public Integer create(@RequestBody Achievement achievement){
        return achievementService.saveReturnId(achievement);
    }

    @PostMapping("/meetups-topic/same")
    public void createForMeetupsSameQuantity(@RequestParam String achievementId,
                                 @RequestParam String[] topics,
                                 @RequestParam String quantity){
        Integer achievementIdInteger = Integer.valueOf(achievementId);
        Integer quantityInteger = Integer.valueOf(quantity);
        achievementService.createForMeetupsSameQuantity(achievementIdInteger, topics, quantityInteger);
    }

    @PutMapping("/{achievementId}")
    public ResponseEntity<Achievement> updateAchievement(@PathVariable("achievementId") Integer achievementId, @RequestBody Achievement achievement) {
        if (achievementService.get(achievementId) == null) {
            return ResponseEntity.badRequest().build();
        }
        achievement.setAchievementId(achievementId);
        achievementService.update(achievement);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{achievementId}")
    public ResponseEntity<Achievement> deleteAchievement(@PathVariable("achievementId") String achievementId){
        Integer idInteger = Integer.valueOf(achievementId);
        if (achievementService.get(idInteger) == null) {
            return ResponseEntity.badRequest().build();
        }
        achievementService.delete(idInteger);
        return ResponseEntity.ok().build();
    }
}