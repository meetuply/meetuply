package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.Topic;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.FilterService;
import ua.meetuply.backend.service.MeetupService;

import javax.annotation.Resource;
import javax.validation.Valid;

@RequestMapping("api/meetups")
@RestController
public class MeetupController {

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private AppUserService appUserService;

    @Resource
    private FilterService filterService;

    @GetMapping()
    public @ResponseBody Iterable<Meetup> getAllMeetups(){
            return meetupService.getAllMeetups();
    }

    @GetMapping("/{startRow}/{endRow}")
    public @ResponseBody Iterable<Meetup> getMeetupsChunk(@PathVariable("startRow") Integer startRow,
                                                          @PathVariable("endRow") Integer endRow)
    {
        return meetupService.getMeetupsChunk(startRow, endRow);
    }

    @GetMapping("/{meetupId}/attendees")
    public @ResponseBody Iterable<AppUser> getAttendees(@PathVariable("meetupId") Integer meetupId){
        return appUserService.getMeetupAttendees(meetupId);
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
    public ResponseEntity<Meetup> updateMeetup(@PathVariable("meetupId") Integer meetupId, @RequestBody Meetup meetup) {
        if (meetupService.getMeetupById(meetupId) == null) {
            return ResponseEntity.badRequest().build();
        }
        meetup.setMeetupId(meetupId);
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

    @PutMapping("/{meetupID}/join")
    public ResponseEntity join(@PathVariable("meetupID") Integer meetupID) throws Exception {
        meetupService.join(meetupID);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{meetupID}/leave")
    public ResponseEntity leave(@PathVariable("meetupID") Integer meetupID) throws Exception {
        meetupService.leave(meetupID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{meetupID}/attendee")
    public @ResponseBody Boolean leave(
            @PathVariable("meetupID") Integer meetupID,
            @RequestParam("id") Integer userID) {
        return meetupService.isAttendee(meetupID, userID);
    }


    @PostMapping("/filters")
    public ResponseEntity<Meetup> createFilter (@Valid @RequestBody Filter filter){
        filter.setUserId(appUserService.getCurrentUserID());
        filterService.createFilter(filter);
        return ResponseEntity.ok().build();
    }

}
