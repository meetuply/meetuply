package ua.meetuply.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.MeetupStateException;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.controller.exception.PermissionException;
import ua.meetuply.backend.dao.FilterDAO;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.model.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MeetupService {

    @Autowired
    private MeetupDAO meetupDao;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private StateService stateService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private FilterDAO filterDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;


    @Transactional
    public void createMeetup(FullMeetup meetup) throws NotFoundException {
        log.debug("Creating meetup");
        meetup.setStateId(stateService.get(State.SCHEDULED).getStateId());
        meetup.setSpeakerId(appUserService.getCurrentUserID());
        meetupDao.saveFull(meetup);

        //notifiy all subscribers the a new meetup is created
        for (Integer user: appUserService.getUserSubscribers(appUserService.getCurrentUserID())) {
            notificationService.sendNotification(user,"subscription_new_meetup_template");
        }

        achievementService.checkOne(AchievementType.MEETUPS);
        achievementService.checkOne(AchievementType.MEETUPS_TOPIC);
    }

    public List<Topic> getMeetupTopics(Integer meetupId){
        log.debug("Getting meetup topics by id");
        return meetupDao.getMeetupTopics(meetupId);
    }


    public List<Meetup> getAllMeetups() {
        log.debug("Getting all meetups");
        return meetupDao.getAll();
    }

    public Meetup getMeetupById(Integer meetupId) {
        log.debug("Getting meetup by id");
        return meetupDao.get(meetupId);
    }

    public void updateMeetup(Meetup meetup) {
        log.debug("Updating meetup");
        meetupDao.update(meetup);
    }

    public void deleteMeetup(Integer meetupId) {
        log.debug("Deleting meetup");
        meetupDao.delete(meetupId);
    }

    public List<Meetup> getUserFutureMeetups(Integer userId){
        log.debug("Getting user future meetups");
        return meetupDao.getUserFutureMeetups(userId);
    }

    public List<Meetup> getUserPastMeetups(Integer userId){
        log.debug("Getting user past meetups");
        return meetupDao.getUserPastMeetups(userId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void join(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw new NotFoundException("Cannot find current user");
        if (meetupDao.get(meetupID) == null) throw new NotFoundException("There is no meetup #" + meetupID);
        meetupDao.join(meetupID, user.getUserId());

        Meetup meetup = meetupDao.get(meetupID);

        notificationService.sendNotification(user.getUserId(),"meetup_subscription_template");
        notificationService.sendNotification(meetup.getSpeakerId(),"new_meetup_atendee_template");

        if (meetup.getMeetupRegisteredAttendees() == meetup.getMeetupMaxAttendees())
            stateService.updateState(meetup, stateService.get(State.BOOKED));
    }

    public Iterable<Meetup> getUserMeetupsBeforeDay(Integer userId, int day) {
        return meetupDao.getUserMeetupsBeforeDay(userId, day);
    }

    public Iterable<Meetup> getMeetupsChunkWithUsernameAndRating(Integer startRow, Integer endRow) {
        return meetupDao.getMeetupsChunkWithUsernameAndRating(startRow, endRow);
    }

    public Iterable<Meetup> getMeetupsChunkActive(Integer startRow, Integer endRow) {
        return meetupDao.getMeetupsChunkActive(startRow, endRow);
    }

    public Iterable<Meetup> getUserMeetupsChunk(Integer startRow, Integer endRow) {
        return meetupDao.getUserMeetupsChunk(appUserService.getCurrentUserID(), startRow, endRow);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void leave(Integer meetupID) throws NotFoundException {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw new NotFoundException("Cannot find current user");
        if (meetupDao.get(meetupID) == null) throw new NotFoundException("There is no meetup #" + meetupID);
        meetupDao.leave(meetupID, user.getUserId());
        Meetup meetup = meetupDao.get(meetupID);

        if (meetup.getMeetupRegisteredAttendees() != meetup.getMeetupMaxAttendees())
            stateService.updateState(meetup, stateService.get(State.SCHEDULED));
    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return meetupDao.isAttendee(meetupID, userID);
    }

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        return meetupDao.findBy(filter);
    }

    public Integer getUserMeetupsNumber(Integer userId) {
        return meetupDao.getUserMeetupsNumber(userId);
    }

    public void cancelMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) {
            throw new NotFoundException("There is no meetup #" + meetupID);
        }

        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin()) {
            State state = stateService.get(meetup.getStateId());
            Set<State> allowedStates = stateService.get(State.BOOKED, State.SCHEDULED, State.TERMINATED);
            if (allowedStates.contains(state)) {
                stateService.updateState(meetup, stateService.get(State.CANCELED));
                for (AppUser user: appUserService.getMeetupAttendees(meetupID)) {
                    emailService.informCancellation(user, meetup);
                    notificationService.sendNotification(user.getUserId(),"meetup_canceled_template");
                }
                notificationService.sendNotification(meetup.getSpeakerId(),"meetup_canceled_template");
            } else {
                throw MeetupStateException.createWith("you cannot switch to Canceled from " + stateService.get(meetup.getStateId()).getName());
            }
        } else {
            throw PermissionException.createWith("you cannot modify not yours meetups");
        }
    }

    public void terminateMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) throw new NotFoundException("There is no meetup #" + meetupID);

        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin())
            if (meetup.getStateId().equals(stateService.get(State.IN_PROGRESS).getStateId()))
                stateService.updateState(meetup, stateService.get(State.TERMINATED));
            else
                throw MeetupStateException.createWith("you cannot switch to Terminated from " + stateService.get(meetup.getStateId()).getName());
        else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public void rescheduleTerminatedMeetup(Meetup meetup) throws Exception {
        Meetup oldMeetup = getMeetupById(meetup.getMeetupId());
        if (oldMeetup == null) throw new NotFoundException("There is no meetup #" + meetup.getMeetupId());


        if (oldMeetup.getSpeakerId() == appUserService.getCurrentUserID()) {
            oldMeetup.setMeetupStartDateTime(meetup.getMeetupStartDateTime());
            oldMeetup.setMeetupFinishDateTime(meetup.getMeetupFinishDateTime());
            if (oldMeetup.getStateId().equals(stateService.get(State.TERMINATED).getStateId()))
                if (oldMeetup.getMeetupRegisteredAttendees() == oldMeetup.getMeetupRegisteredAttendees())
                    stateService.updateState(oldMeetup, stateService.get(State.BOOKED));
                else
                    stateService.updateState(oldMeetup, stateService.get(State.SCHEDULED));
            else
                throw MeetupStateException.createWith("you cannot switch to Scheduled/Booked from " + stateService.get(meetup.getStateId()).getName());
        } else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public List<Meetup> findMeetupsByCriteria(float ratingFrom, float ratingTo, Timestamp dateFrom, Timestamp dateTo,
                                              List<Topic> topics, Integer userId) {
        Filter filterDto = new Filter();
        filterDto.setDateFrom(dateFrom);
        filterDto.setDateTo(dateTo);
        filterDto.setRatingFrom(ratingFrom);
        filterDto.setRatingTo(ratingTo);
        filterDto.setTopics(topics);
        filterDto.setUserId(userId);
        return meetupDao.findBy(filterDto);
    }

    public Timestamp getTimestampFromString(String dateTime) {
        Timestamp timestamp = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date parseDate = dateFormat.parse(dateTime);
            timestamp = new java.sql.Timestamp(parseDate.getTime());
        } catch (ParseException e) {
            log.error("Parse exception occured.");
        }
        return timestamp;
    }

    public List<Meetup> findBy(Filter filter) {
        return meetupDao.findBy(filter);
    }

    public List<Filter> getAllFilters() {
        return filterDAO.getAll();
    }

}