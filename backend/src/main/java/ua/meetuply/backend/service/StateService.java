package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.dao.StateDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.State;
import ua.meetuply.backend.model.State.StateNames;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@CacheConfig(cacheNames = {"states"})
public class StateService {

    @Autowired
    StateDAO stateDAO;

    @Autowired
    MeetupDAO meetupDAO;

    private Map<String, State> states = new HashMap<>();

    @Cacheable
    public Map<String, State> getAll() {
        return stateDAO.getAll();
    }

    public State get(String name) {
        return getAll().get(name);
    }

    public State get(Integer id) {
        return getAll().values().stream().filter(s -> s.getStateId().equals(id)).findFirst().orElse(null);
    }

    public void updateState(List<Meetup> meetups, State state) {
        for (Meetup m : meetups) updateState(m, state);
    }

    public void updateState(Meetup meetups, State state) {
        meetups.setStateId(state.getStateId());
        meetupDAO.update(meetups);
    }

    public void cancelFutureMeetupsOf(AppUser user) {
        updateState(meetupDAO.futureScheduledAndBookedMeetupsOf(user), get(StateNames.CANCELED.name));
    }

    public void terminateCurrentMeetupsOf(AppUser user) {
        updateState(meetupDAO.currentMeetupsOf(user), get(StateNames.TERMINATED.name));
    }


    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 1500)
    public void cancelNotEnoughAttendees() {
        updateState(meetupDAO.notEnoughAttendees1Hour(), get(StateNames.CANCELED.name));
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void updateInProgress() {
        updateState(meetupDAO.goingToStart(), get(StateNames.IN_PROGRESS.name));
    }

    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 500)
    public void updatePassed() {
        updateState(meetupDAO.goingToFinish(), get(StateNames.PASSED.name));
    }
}
