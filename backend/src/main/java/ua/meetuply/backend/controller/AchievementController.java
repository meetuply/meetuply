package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Achievement;
import ua.meetuply.backend.service.AchievementService;

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

    @PostMapping("/create")
    public ResponseEntity<Achievement> create(@RequestBody Achievement achievement){
        achievementService.create(achievement);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Achievement> deleteAchievement(@PathVariable("achievementId") Integer achievementId){
        if (achievementService.get(achievementId) == null) {
            return ResponseEntity.badRequest().build();
        }
        achievementService.delete(achievementId);
        return ResponseEntity.ok().build();
    }
}