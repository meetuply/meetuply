package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.dao.StateDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.State;

import java.util.List;

@Component
public class StateService {

    @Autowired
    StateDAO stateDAO;

    @Autowired
    MeetupDAO meetupDAO;

    public void updateState(List<Meetup> meetups, State newState) {
        for (Meetup m: meetups) {
            m.setStateId(newState.getStateId());
            meetupDAO.update(m);
        }
    }

    public void updateState(Meetup meetups, State newState) {
        meetups.setStateId(newState.getStateId());
        meetupDAO.update(meetups);
    }

    public void update(Meetup meetup, State state) {
        updateState(meetup, state);
    }

    public void cancelFutureMeetupsOf(AppUser user){
        updateState(meetupDAO.futureScheduledAndBookedMeetupsOf(user), stateDAO.get("Canceled"));
    }

    public void terminateCurrentMeetupsOf(AppUser user){
        updateState(meetupDAO.currentMeetupsOf(user), stateDAO.get("Terminated"));
    }


    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 1500)
    public void cancelNotEnoughAttendees() {
        updateState(meetupDAO.notEnoughAttendees1Hour(), stateDAO.get("Canceled"));
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void updateInProgress() {
        updateState(meetupDAO.goingToStart(), stateDAO.get("In progress"));
    }

    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 500)
    public void updatePassed() {
        updateState(meetupDAO.goingToFinish(), stateDAO.get("Passed"));
    }

    public Iterable<State> getAll() {
        return stateDAO.getAll();
    }
}
