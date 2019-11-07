package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.MeetupStateException;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.controller.exception.PermissionException;
import ua.meetuply.backend.dao.FilterDAO;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.model.*;
import ua.meetuply.backend.model.State.StateNames;

import java.sql.Timestamp;
import java.util.List;

@Component
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

    public void createMeetup(FullMeetup meetup) {
        meetup.setStateId(stateService.get(StateNames.SCHEDULED.name).getStateId());
        meetup.setSpeakerId(appUserService.getCurrentUserID());

        meetupDao.saveFull(meetup);
        achievementService.checkOne(AchievementType.MEETUPS);
        achievementService.checkMultiple();
    }

    public List<Topic> getMeetupTopics(Integer i){
        return meetupDao.getMeetupTopics(i);
    }


    public List<Meetup> getAllMeetups() {
        return meetupDao.getAll();
    }

    public Meetup getMeetupById(Integer meetupId) {
        return meetupDao.get(meetupId);
    }

    public void updateMeetup(Meetup meetup) {
        meetupDao.update(meetup);
    }

    public void deleteMeetup(Integer meetupId) {
        meetupDao.delete(meetupId);
    }

    public List<Meetup> getUserFutureMeetups(Integer userId){return meetupDao.getUserFutureMeetups(userId);}

    public List<Meetup> getUserPastMeetups(Integer userId){return meetupDao.getUserPastMeetups(userId);}

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void join(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw NotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw NotFoundException.createWith("There is no meetup #" + meetupID);
        meetupDao.join(meetupID, user.getUserId());
        Meetup meetup = meetupDao.get(meetupID);
        if (meetup.getMeetupRegisteredAttendees() == meetup.getMeetupMaxAttendees())
            stateService.updateState(meetup, stateService.get(StateNames.BOOKED.name));
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
    public void leave(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw NotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw NotFoundException.createWith("There is no meetup #" + meetupID);
        meetupDao.leave(meetupID, user.getUserId());
        Meetup meetup = meetupDao.get(meetupID);
        if (meetup.getMeetupRegisteredAttendees() != meetup.getMeetupMaxAttendees())
            stateService.updateState(meetup, stateService.get(StateNames.SCHEDULED.name));
    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return meetupDao.isAttendee(meetupID, userID);
    }

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        return meetupDao.findMeetupsByFilter(filter);
    }

    public Integer getUserMeetupsNumber(Integer userId) {
        return meetupDao.getUserMeetupsNumber(userId);
    }

    public void cancelMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) throw NotFoundException.createWith("There is no meetup #" + meetupID);

        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin())
            if (meetup.getStateId().equals(stateService.get(StateNames.BOOKED.name).getStateId())
                    || meetup.getStateId().equals(stateService.get(StateNames.SCHEDULED.name).getStateId())
                    || meetup.getStateId().equals(stateService.get(StateNames.TERMINATED.name).getStateId()))
                stateService.updateState(meetup, stateService.get(StateNames.CANCELED.name));
            else
                throw MeetupStateException.createWith("you cannot switch to Canceled from " + stateService.get(meetup.getStateId()).getName());
        else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public void terminateMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) throw NotFoundException.createWith("There is no meetup #" + meetupID);

        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin())
            if (meetup.getStateId().equals(stateService.get(StateNames.IN_PROGRESS.name).getStateId()))
                stateService.updateState(meetup, stateService.get(StateNames.TERMINATED.name));
            else
                throw MeetupStateException.createWith("you cannot switch to Terminated from " + stateService.get(meetup.getStateId()).getName());
        else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public void rescheduleTerminatedMeetup(Meetup meetup) throws Exception {
        Meetup oldMeetup = getMeetupById(meetup.getMeetupId());
        if (oldMeetup == null) throw NotFoundException.createWith("There is no meetup #" + meetup.getMeetupId());


        if (oldMeetup.getSpeakerId() == appUserService.getCurrentUserID()) {
            oldMeetup.setMeetupStartDateTime(meetup.getMeetupStartDateTime());
            oldMeetup.setMeetupFinishDateTime(meetup.getMeetupFinishDateTime());
            if (oldMeetup.getStateId().equals(stateService.get(StateNames.TERMINATED.name).getStateId()))
                if (oldMeetup.getMeetupRegisteredAttendees() == oldMeetup.getMeetupRegisteredAttendees())
                    stateService.updateState(oldMeetup, stateService.get(StateNames.BOOKED.name));
                else
                    stateService.updateState(oldMeetup, stateService.get(StateNames.SCHEDULED.name));
            else
                throw MeetupStateException.createWith("you cannot switch to Scheduled/Booked from " + stateService.get(meetup.getStateId()).getName());
        } else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public List<Meetup> findMeetupsByCriteria(Double rating, Timestamp dateFrom, Timestamp dateTo) {
        Filter filterDto = new Filter();
        //filterDto.setRating(rating);
        filterDto.setDateFrom(dateFrom);
        filterDto.setDateTo(dateTo);
        return meetupDao.findMeetupsByFilter(filterDto);
    }

    public List<Meetup> findBy(Filter filter) {
        return meetupDao.findBy(filter);
    }

    public List<Filter> getAllFilters() {
        return filterDAO.getAll();
    }

}