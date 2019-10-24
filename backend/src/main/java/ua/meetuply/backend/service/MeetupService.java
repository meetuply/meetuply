package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.controller.exception.MeetupNotFoundException;
import ua.meetuply.backend.controller.exception.UserNotFoundException;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Meetup;

import java.util.List;

@Component
public class MeetupService {

    @Autowired
    MeetupDAO meetupDao;

    @Autowired
    AppUserService appUserService;

    //todo add state logic
    public void createMeetup(Meetup meetup) {

        meetup.setStateId(1);

        //meetup.setSpeakerId(1);
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

    public void join(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw UserNotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw MeetupNotFoundException.createWith(meetupID);
        meetupDao.join(meetupID, user.getUserId());
    }


    public Iterable<Meetup> getMeetupsChunk(Integer startRow, Integer endRow) {
        return meetupDao.getMeetupsChunk(startRow, endRow);
    }
  
    public void leave(Integer meetupID) throws Exception {
        AppUser user = appUserService.getCurrentUser();
        if (user == null) throw UserNotFoundException.createWith("current");
        if (meetupDao.get(meetupID) == null) throw MeetupNotFoundException.createWith(meetupID);
        meetupDao.leave(meetupID, user.getUserId());
    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return meetupDao.isAttendee(meetupID, userID);
    }
}
