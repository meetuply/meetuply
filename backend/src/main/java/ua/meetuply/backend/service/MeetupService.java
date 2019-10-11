package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.formbean.MeetupForm;
import ua.meetuply.backend.model.Meetup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MeetupService {

    @Autowired
    MeetupDAO meetupDao;

    @Autowired
    AppUserDAO appUserDAO;

    public void createMeetup(MeetupForm meetupForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(meetupForm.getMeetupStartDateTime(), formatter);
        LocalDateTime finishDateTime = LocalDateTime.parse(meetupForm.getMeetupStartDateTime(), formatter);
        Meetup meetup = new Meetup(null, meetupForm.getMeetupPlace(), meetupForm.getMeetupTitle(),
                0,meetupForm.getMeetupMinAttendees(),
                meetupForm.getMeetupMaxAttendees(),startDateTime,
                finishDateTime,meetupForm.getState(), getCurrentUserID());
        meetupDao.save(meetup);
    }

    public List<Meetup> showAllMeetups(){
        return meetupDao.getAll();
    }

    public Meetup getMeetupById(Integer id) {
        return meetupDao.get(id);}

        public int getCurrentUserID(){
            String email="";
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();         if (principal instanceof UserDetails) {
                email = ((UserDetails)principal).getUsername();
            } else {
                email = principal.toString();
            }
            int userId = appUserDAO.getUserIdByEmail(email);
            return userId;
        }
}