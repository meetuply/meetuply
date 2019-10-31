package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.MeetupNotFoundException;
import ua.meetuply.backend.controller.exception.MeetupStateException;
import ua.meetuply.backend.controller.exception.PermissionException;
import ua.meetuply.backend.controller.exception.UserNotFoundException;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.dao.StateDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;

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
    private StateDAO stateDAO;

    public void createMeetup(Meetup meetup) {
        meetup.setStateId(stateDAO.get("Scheduled").getStateId());
        meetup.setSpeakerId(appUserService.getCurrentUserID());
        meetupDao.save(meetup);
    }

    public List<Meetup> getAllMeetups() {
        return meetupDao.getAll();
    }

    public Meetup getMeetupById(Integer id) {
        return meetupDao.get(id);
    }

    public void updateMeetup(Meetup meetup){
        meetupDao.update(meetup);
    }

    public void deleteMeetup(Integer id){
        meetupDao.delete(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void join(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw UserNotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw MeetupNotFoundException.createWith(meetupID);
        meetupDao.join(meetupID, user.getUserId());
        Meetup meetup = meetupDao.get(meetupID);
        if (meetup.getMeetupRegisteredAttendees() == meetup.getMeetupMaxAttendees())
            stateService.update(meetup, stateDAO.get("Booked"));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void leave(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw UserNotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw MeetupNotFoundException.createWith(meetupID);
        meetupDao.leave(meetupID, user.getUserId());
        Meetup meetup = meetupDao.get(meetupID);
        if (meetup.getMeetupRegisteredAttendees() != meetup.getMeetupMaxAttendees())
            stateService.update(meetup, stateDAO.get("Scheduled"));
    }

    public Iterable<Meetup> getMeetupsChunk(Integer startRow, Integer endRow) {
        return meetupDao.getMeetupsChunk(startRow, endRow);
    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return meetupDao.isAttendee(meetupID, userID);
    }

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        return meetupDao.findMeetupsByFilter(filter);
    }

    public void cancelMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) throw MeetupNotFoundException.createWith(meetupID);

        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin())
            if (meetup.getStateId().equals(stateDAO.get("Booked").getStateId())
                || meetup.getStateId().equals(stateDAO.get("Scheduled").getStateId())
                || meetup.getStateId().equals(stateDAO.get("Terminated").getStateId()))
                stateService.updateState(meetup, stateDAO.get("Canceled"));
            else throw MeetupStateException.createWith("you cannot switch to Canceled from " + stateDAO.get(meetup.getStateId()).getName());
        else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public void terminateMeetup(Integer meetupID) throws Exception {
        Meetup meetup = getMeetupById(meetupID);
        if (meetup == null) throw MeetupNotFoundException.createWith(meetupID);
        if (meetup.getSpeakerId() == appUserService.getCurrentUserID() || appUserService.isAdmin())
            if (meetup.getStateId().equals(stateDAO.get("In progress").getStateId()))
                stateService.updateState(meetup, stateDAO.get("Terminated"));
            else throw MeetupStateException.createWith("you cannot switch to Terminated from " + stateDAO.get(meetup.getStateId()).getName());
        else throw PermissionException.createWith("you cannot modify not yours meetups");
    }

    public void rescheduleTerminatedMeetup(Meetup meetup) throws Exception {
        Meetup oldMeetup = getMeetupById(meetup.getMeetupId());
        if (oldMeetup == null) throw MeetupNotFoundException.createWith(meetup.getMeetupId());
        if (oldMeetup.getSpeakerId() == appUserService.getCurrentUserID()) {
            oldMeetup.setMeetupStartDateTime(meetup.getMeetupStartDateTime());
            oldMeetup.setMeetupFinishDateTime(meetup.getMeetupFinishDateTime());
            if (oldMeetup.getStateId().equals(stateDAO.get("Terminated").getStateId()))
                if (oldMeetup.getMeetupRegisteredAttendees() == oldMeetup.getMeetupRegisteredAttendees())
                    stateService.updateState(oldMeetup, stateDAO.get("Booked"));
                else
                    stateService.updateState(oldMeetup, stateDAO.get("Scheduled"));
            else
                throw MeetupStateException.createWith("you cannot switch to Scheduled/Booked from " + stateDAO.get(meetup.getStateId()).getName());
        } else throw PermissionException.createWith("you cannot modify not yours meetups");
    }
}
