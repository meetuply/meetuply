package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.dao.FeedbackDAO;
import ua.meetuply.backend.model.Feedback;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FeedbackService {

    @Autowired
    FeedbackDAO feedbackDAO;

    @Autowired
    AppUserService appUserService;

    public void createFeedback(Feedback feedback) {
        feedback.setDate(LocalDateTime.now());
        feedback.setFeedbackBy(appUserService.getCurrentUserID());
        feedbackDAO.save(feedback);
    }

    public void updateFeedback(Feedback feedback){
        feedbackDAO.update(feedback);
    }

    public void deleteFeedback(Integer id){
        feedbackDAO.delete(id);
    }

    public List<Feedback> getFeedbacks() {
        return feedbackDAO.getAll();
    }

    public Feedback getFeedbackById(Integer id) {return feedbackDAO.get(id);}

    public List<Feedback> getFeedbacksByAuthor(Integer id) {return feedbackDAO.getByAuthor(id);}

    public List<Feedback> getFeedbacksByFeedbackTo(Integer id) {return feedbackDAO.getByFeedbackTo(id);}

    public Iterable<Feedback> getFeedbacksByTo(Integer idby, Integer idto) { return feedbackDAO.getByTo(idby, idto);}

    public List<Integer> getFeedbacksWaiting(Integer attendee) throws NotFoundException {return feedbackDAO.getFeedbacksWaiting(attendee);}
}
