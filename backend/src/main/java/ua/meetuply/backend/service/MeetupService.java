package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.controller.exception.MeetupNotFoundException;
import ua.meetuply.backend.controller.exception.UserNotFoundException;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;

import java.sql.Timestamp;
import java.util.List;

@Component
public class MeetupService {

    @Autowired
    private MeetupDAO meetupDao;

    @Autowired
    private AppUserService appUserService;

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

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        return meetupDao.findMeetupsByFilter(filter);
    }

    public List<Meetup> findMeetupsByCriterias(Double rating, Timestamp dateFrom, Timestamp dateTo) {
        Filter filterDto = new Filter();
        filterDto.setRating(rating);
        filterDto.setDateFrom(dateFrom);
        filterDto.setDateTo(dateTo);
        return meetupDao.findMeetupsByFilter(filterDto);
    }
}
