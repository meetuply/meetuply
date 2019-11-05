package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.FilterService;
import ua.meetuply.backend.service.MeetupService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

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
    public @ResponseBody Iterable<Meetup> getMeetupsChunkWithUsernameAndRating(
            @PathVariable("startRow") Integer startRow,
            @PathVariable("endRow") Integer endRow)
    {
        return meetupService.getMeetupsChunkWithUsernameAndRating(startRow, endRow);
    }

    @GetMapping("/{meetupId}/attendees")
    public @ResponseBody Iterable<AppUser> getAttendees(@PathVariable("meetupId") Integer meetupId){
        return appUserService.getMeetupAttendees(meetupId);
    }

    @PostMapping("/create")
    public ResponseEntity createMeetup(@Valid @RequestBody Meetup meetup){

        meetupService.createMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{meetupId}")
    public Meetup showOne(@PathVariable("meetupId") Integer meetupId) {
        return meetupService.getMeetupById(meetupId);
    }

    @PutMapping("/{meetupId}")
    public ResponseEntity updateMeetup(@PathVariable("meetupId") Integer meetupId, @RequestBody Meetup meetup) {
        if (meetupService.getMeetupById(meetupId) == null) {
            return ResponseEntity.notFound().build();
        }
        meetup.setMeetupId(meetupId);
        meetupService.updateMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/future/{userId}")
    @ResponseBody
    public Iterable<Meetup> getUserFutureMeetups(@PathVariable("userId") Integer userId){
        return meetupService.getUserFutureMeetups(userId);
    }

    @GetMapping("/past/{userId}")
    @ResponseBody
    public Iterable<Meetup> getUserPastMeetups(@PathVariable("userId") Integer userId){
        return meetupService.getUserPastMeetups(userId);
    }

    @DeleteMapping("/{meetupId}")
    public ResponseEntity deleteMeetup(@PathVariable("meetupId") Integer meetupId){
        if (meetupService.getMeetupById(meetupId) == null) {
            return ResponseEntity.notFound().build();
        }
        meetupService.deleteMeetup(meetupId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{meetupID}/join")
    public ResponseEntity join(@PathVariable("meetupID") Integer meetupID) throws Exception {
        System.out.println(meetupID);
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

    //TODO rename url
    @GetMapping("/filter-test")
    public @ResponseBody
    List<Meetup> getMeetupsByFilter(@RequestParam(value = "filter") Integer filterId, Model model) {
        Filter filter = filterService.getFilter(filterId);
        List<Meetup> meetups = meetupService.findMeetupsByFilter(filter);
        model.addAttribute(meetups);
        //TODO decide what page will be here
        //return "";
        return meetups;
    }

    //TODO rename url
    @GetMapping("/criteria-test")
    public @ResponseBody
    List<Meetup> getMeetupsByCriteria(@RequestParam(value = "rating", required = false) Double rating,
                                      @RequestParam(value = "date-from", required = false) Timestamp dateFrom,
                                      @RequestParam(value = "date-to", required = false) Timestamp dateTo,
                                      Model model) {
        List<Meetup> meetups = meetupService.findMeetupsByCriteria(rating, dateFrom, dateTo);
        model.addAttribute(meetups);
        return meetups;
    }

    @PatchMapping("/{meetupID}/{action}")
    public ResponseEntity changeState
            (@PathVariable("meetupID") Integer meetupID,
             @PathVariable("action") String action,
             @RequestBody Meetup meetup) throws Exception {
        switch (action) {
            case "cancel":
                meetupService.cancelMeetup(meetupID);
                break;
            case "terminate":
                meetupService.terminateMeetup(meetupID);
                break;
            case "reschedule":
                meetupService.rescheduleTerminatedMeetup(meetup);
                break;
        }
        return ResponseEntity.ok().build();
    }
}