package ua.meetuply.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.Topic;
import ua.meetuply.backend.model.*;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.FilterService;
import ua.meetuply.backend.service.MeetupService;
import ua.meetuply.backend.service.TopicService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RequestMapping("api/meetups")
@RestController
public class MeetupController {

    @Autowired
    private MeetupService meetupService;

    @Autowired
    private AppUserService appUserService;

    @Resource
    private FilterService filterService;

    @Resource
    private TopicService topicService;

    @GetMapping()
    public Iterable<Meetup> getAllMeetups() {
        log.debug("Getting all meetups");
        return meetupService.getAllMeetups();
    }

    @GetMapping("/{startRow}/{endRow}")
    public Iterable<Meetup> getMeetupsChunkWithUsernameAndRating(
            @PathVariable("startRow") Integer startRow,
            @PathVariable("endRow") Integer endRow) {
        log.debug("Getting meetups chunk with username and rating");
        return meetupService.getMeetupsChunkWithUsernameAndRating(startRow, endRow);
    }

    @GetMapping("/active/{startRow}/{endRow}")
    public Iterable<Meetup> getMeetupsChunkActive(
            @PathVariable("startRow") Integer startRow,
            @PathVariable("endRow") Integer endRow) {
        log.debug("Getting future active meetups");
        return meetupService.getMeetupsChunkActive(startRow, endRow);
    }

    @GetMapping("user/{startRow}/{endRow}")
    public Iterable<Meetup> getUserMeetupsChunk(
            @PathVariable("startRow") Integer startRow,
            @PathVariable("endRow") Integer endRow) {
        log.debug("Getting user's meetups");
        return meetupService.getUserMeetupsChunk(startRow, endRow);
    }

    @GetMapping("/{meetupId}/attendees")
    public Iterable<AppUser> getAttendees(@PathVariable("meetupId") Integer meetupId) {
        log.debug("Getting meetups attendees");
        return appUserService.getMeetupAttendees(meetupId);
    }


    @GetMapping("/{meetupId}/topics")
    public Iterable<Topic> getTopics(@PathVariable("meetupId") Integer meetupId) {
        log.debug("Getting meetup topics");
        return meetupService.getMeetupTopics(meetupId);
    }

    @GetMapping("/soon/{userId}/{day}")
    public Iterable<Meetup> getMeetupsBeforeDay(@PathVariable("userId") Integer userId,
                                         @PathVariable("day") int day) {
        log.debug("Getting meetups for {} closest days", day);
        return meetupService.getUserMeetupsBeforeDay(userId, day);
    }

    @PostMapping()
    public ResponseEntity createMeetup(@RequestBody @Valid FullMeetup meetup) throws NotFoundException {
        log.debug("Creating meetup");
        meetupService.createMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{meetupId}")
    public Meetup showOne(@PathVariable("meetupId") Integer meetupId) {
        log.debug("Getting meetup by id");
        return meetupService.getMeetupById(meetupId);
    }

    @PutMapping("/{meetupId}")
    public ResponseEntity updateMeetup(@PathVariable("meetupId") Integer meetupId, @RequestBody Meetup meetup) {
        log.debug("Updating meetup");
        if (meetupService.getMeetupById(meetupId) == null) {
            log.error("Meetup not found by provided id: {}", meetupId);
            return ResponseEntity.notFound().build();
        }
        meetup.setMeetupId(meetupId);
        meetupService.updateMeetup(meetup);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/future/{userId}")
    public Iterable<Meetup> getUserFutureMeetups(@PathVariable("userId") Integer userId) {
        return meetupService.getUserFutureMeetups(userId);
    }

    @GetMapping("/past/{userId}")
    public Iterable<Meetup> getUserPastMeetups(@PathVariable("userId") Integer userId) {
        log.debug("Getting user past meetups");
        return meetupService.getUserPastMeetups(userId);
    }

    @DeleteMapping("/{meetupId}")
    public ResponseEntity deleteMeetup(@PathVariable("meetupId") Integer meetupId) {
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
    public  Boolean leave(
            @PathVariable("meetupID") Integer meetupID,
            @RequestParam("id") Integer userID) {
        return meetupService.isAttendee(meetupID, userID);
    }

    @GetMapping("/filters")
    public List<Filter> filters() {
        return meetupService.getAllFilters();
    }

    @PostMapping("/filters")
    public ResponseEntity<Meetup> createFilter(@Valid @RequestBody Filter filter) {
        filter.setUserId(appUserService.getCurrentUserID());
        filterService.createFilter(filter);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter/search")
    public @ResponseBody
    Iterable<Meetup> getMeetupsByFilter(@RequestParam(value = "filter") Integer filterId) {
        Filter filter = filterService.getFilter(filterId);
        return meetupService.findMeetupsByFilter(filter);
    }

    @GetMapping("/criteria/search")
    public @ResponseBody
    List<Meetup> getMeetupsByCriteria(@RequestParam(value = "ratingFrom", required = false) Float ratingFrom,
                                      @RequestParam(value = "ratingTo", required = false) Float ratingTo,
                                      @RequestParam(value = "dateFrom", required = false) String dateFrom,
                                      @RequestParam(value = "dateTo", required = false) String dateTo,
                                      @RequestParam(value = "topics", required = false) List<Integer> topics) {

        Timestamp dateFromTimestamp = meetupService.getTimestampFromString(dateFrom);
        Timestamp dateToTimestamp = meetupService.getTimestampFromString(dateTo);
        return meetupService.findMeetupsByCriteria(ratingFrom, ratingTo, dateFromTimestamp, dateToTimestamp,
                topicService.getTopicListFromIdList(topics), appUserService.getCurrentUserID());
    }

    @GetMapping("/userFilters")
    public @ResponseBody
    Iterable<Filter> getUsersFilters() {
        return filterService.getUsersFilter(appUserService.getCurrentUserID());
    }

    @PatchMapping("/{meetupID}/action={action}")
    public ResponseEntity changeState
            (@PathVariable("meetupID") Integer meetupID,
             @PathVariable("action") String action,
             @RequestBody(required = false) Meetup meetup) throws Exception {
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