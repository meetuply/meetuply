package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Feedback;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.FeedbackService;

import javax.validation.Valid;

@RequestMapping("api/feedbacks")
@Transactional
@RestController
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    AppUserService appUserService;

    @GetMapping()
    public @ResponseBody
    Iterable<Feedback> getAllFeedbacks() {
        return feedbackService.getFeedbacks();
    }

    @GetMapping("/{feedback-id}")
    public Feedback getFeedback(@PathVariable("feedback-id") Integer feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @GetMapping("/{user-id}")
    public @ResponseBody
    Iterable<Feedback> getFeedbackByFeedbackTo(@PathVariable("user-id") Integer userId) {
        return feedbackService.getFeedbacksByFeedbackTo(userId);
    }

    @GetMapping("/my")
    public @ResponseBody
    Iterable<Feedback> getFeedbackByAuthor() {
        return feedbackService.getFeedbacksByAuthor(appUserService.getCurrentUserID());
    }

    @GetMapping("/{user-id}/my")
    public @ResponseBody
    Iterable<Feedback> getFeedbackFromCurrentTo(@PathVariable("user-id") Integer userId) {
        return feedbackService.getFeedbacksByTo(appUserService.getCurrentUserID(), userId);
    }

    @GetMapping("/{user-id}/feedback-waiting")
    @ResponseBody
    public Iterable<Integer> getUserFeedbackWaiting(@PathVariable("user-id") Integer userId) {
        return feedbackService.getFeedbacksWaiting(userId);
    }

    @PostMapping("/{user-id}")
    public ResponseEntity createFeedback(@PathVariable("user-id") Integer userId,
                                         @Valid @RequestBody Feedback feedback) {
        if (feedback == null ||
                feedback.getFeedbackContent().length() <= 0 ||
                appUserService.getUser(userId) == null ||
                appUserService.getCurrentUserID() == userId) {
            return ResponseEntity.badRequest().build();
        }
        feedbackService.createFeedback(feedback);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{feedback-id}")
    public ResponseEntity updateFeedback(@PathVariable("feedback-id") Integer feedbackId,
                                         @RequestBody Feedback feedback) {
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin"))) {
            return ResponseEntity.badRequest().build();
        }
        if (feedback == null || feedbackService.getFeedbackById(feedbackId) == null) {
            return ResponseEntity.badRequest().build();
        }
        feedback.setFeedbackId(feedbackId);
        feedbackService.updateFeedback(feedback);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{feedback-id}")
    public ResponseEntity deleteFeedback(@PathVariable("feedback-id") Integer feedbackId) {
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin"))) {
            return ResponseEntity.badRequest().build();
        }
        if (feedbackService.getFeedbackById(feedbackId) == null) {
            return ResponseEntity.badRequest().build();
        }
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok().build();
    }

}
