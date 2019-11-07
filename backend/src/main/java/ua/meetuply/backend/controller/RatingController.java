package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Rating;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.RatingService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequestMapping("api/ratings")
@Transactional
@RestController
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping()
    public @ResponseBody
    Iterable<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @GetMapping("/{user-id}")
    public @ResponseBody
    Iterable<Rating> getUserRating(@PathVariable("user-id") Integer id) {
        return ratingService.getUserRating(id);
    }

    @GetMapping("/{user-id}/my")
    public Rating getRatingByCurrentUser(@PathVariable("user-id") Integer id) {
        return ratingService.getRatingByUserIds(appUserService.getCurrentUserID(),id);
    }

    @GetMapping("/{id-by}/{id-to}")
    public Rating getRatingByTo(@PathVariable("id-by") Integer idby,
                                         @PathVariable("id-to") Integer idto) {
        return ratingService.getRatingByUserIds(idby,idto);
    }

    @GetMapping("/{user-id}/avg")
    public int getUserRatingAvg(@PathVariable("user-id") Integer id) {
        return ratingService.getUsersAvgRating(id);
    }

    @PostMapping("/{user-id}")
    public ResponseEntity<Rating> createNewRating(@PathVariable("user-id") Integer id, @Valid @RequestBody Rating rating){
        if (appUserService.getCurrentUserID()==id){
            return ResponseEntity.badRequest().build();
        }
        ratingService.createRating(rating, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{user-id}")
    public ResponseEntity update(@PathVariable("user-id") Integer userId,
                                         @RequestBody Rating rating) {
        if (appUserService.getCurrentUserID()==userId) {
            return ResponseEntity.badRequest().build();
        }
        if (ratingService.getRatingByUserIds(appUserService.getCurrentUserID(),userId) == null){
            ratingService.createRating(rating, userId);
            return ResponseEntity.ok().build();
        }
        rating.setRatedBy(appUserService.getCurrentUserID());
        rating.setRatedUser(userId);
        rating.setDate(LocalDateTime.now());
        ratingService.updateRating(rating);
        return ResponseEntity.ok().build();
    }
}
