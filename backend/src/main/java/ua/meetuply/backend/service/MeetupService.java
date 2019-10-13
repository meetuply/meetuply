package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.MeetupDAO;
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
}
