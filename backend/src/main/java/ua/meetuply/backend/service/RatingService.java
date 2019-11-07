package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.RatingDAO;
import ua.meetuply.backend.model.Rating;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RatingService {
    @Autowired
    RatingDAO ratingDAO;

    @Autowired
    AppUserService appUserService;

    public void createRating(Rating rating, Integer idrated) {
        rating.setDate(LocalDateTime.now());
        rating.setRatedBy(appUserService.getCurrentUserID());
        rating.setRatedUser(idrated);
        ratingDAO.save(rating);
    }

    public List<Rating> getAllRatings() {
        return ratingDAO.getAll();
    }

    public List<Rating> getUserRating(Integer id){
        return ratingDAO.getByRatedUserId(id);
    }

    public int getUsersAvgRating(Integer id){
        List<Rating> ratings = getUserRating(id);
        int result=0;
        for (Rating rating: ratings){
            result+=rating.getValue();
        }
        if(ratings.size() > 0){
            result/=ratings.size();
        }
        return result;
    }

    public Rating getRatingByUserIds(Integer idby, Integer idrated) {
        return ratingDAO.getByUserIds(idby, idrated);
    }

    public void updateRating(Rating rating){
        ratingDAO.update(rating);
    }

    public void deleteRating(Integer id){
        ratingDAO.delete(id);
    }
}
