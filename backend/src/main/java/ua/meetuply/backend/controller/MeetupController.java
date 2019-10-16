package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.Topic;
import ua.meetuply.backend.service.MeetupService;

import javax.validation.Valid;

@RequestMapping("api/meetups")
@RestController
public class MeetupController {

    @Autowired @Lazy
    MeetupService meetupService;

    @GetMapping()
    public @ResponseBody Iterable<Meetup> getAllMeetups(){
            return meetupService.getAllMeetups();
    }

    @GetMapping("/create")
    public String showForm() {
        return "creating meetup";
    }

    @PostMapping("/create")
    public ResponseEntity<Meetup> createMeetup(@Valid @RequestBody Meetup meetup){
        meetupService.createMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{meetupId}")
    public Meetup showOne(@PathVariable("meetupId") Integer meetupId) {
        return meetupService.getMeetupById(meetupId);
    }

    @PutMapping("/{meetupId}")
    public ResponseEntity<Topic> updateTopic(@PathVariable("topicId") Integer meetupId, @RequestBody Meetup meetup) {
        if (meetupService.getMeetupById(meetupId) == null) {
            ResponseEntity.badRequest().build();
        }
        meetupService.updateMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{meetupId}")
    public ResponseEntity<Topic> deleteTopic(@PathVariable("meetupId") Integer meetupId){
        if (meetupService.getMeetupById(meetupId) == null) {
            ResponseEntity.badRequest().build();
        }
        meetupService.deleteMeetup(meetupId);
        return ResponseEntity.ok().build();
    }
}
