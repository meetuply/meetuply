package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Rating;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.RatingService;

import javax.validation.Valid;

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

    @GetMapping("/{user-id}/current")
    public Rating getRatingByCurrentUser(@PathVariable("user-id") Integer id) {
        return ratingService.getRatingByUserIds(appUserService.getCurrentUserID(),id);
    }

    @GetMapping("/{user-id}/avg")
    public int getUserRatingAvg(@PathVariable("user-id") Integer id) {
        return ratingService.getUsersAvgRating(id);
    }

    @PostMapping("/{user-id}/create")
    public ResponseEntity<Rating> createNewRating(@PathVariable("user-id") Integer id, @Valid @RequestBody Rating rating){
        ratingService.createRating(rating, id);
        return ResponseEntity.ok().build();
    }
}
