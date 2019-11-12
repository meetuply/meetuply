package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.dao.MeetupDAO;
import ua.meetuply.backend.dao.StateDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.State;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StateService {

    @Autowired
    StateService stateService;

    @Autowired
    StateDAO stateDAO;

    @Autowired
    MeetupDAO meetupDAO;

    @Cacheable("states")
    public Map<String, State> getAll() {
        return stateDAO.getAll();
    }

    @CacheEvict(value = "states", allEntries = true)
    public void evictStatesCache() {}

    public Set<State> get(String... names) throws NotFoundException {
        Set<State> states = new HashSet<>();
        for (String name: names) {
            states.add(get(name));
        }
        return states;
    }

    public State get(String name) throws NotFoundException {
        if (!stateService.getAll().containsKey(name)) {
            stateService.evictStatesCache();
        }
        if (stateService.getAll().containsKey(name)) {
            return stateService.getAll().get(name);
        } else {
            throw new NotFoundException("State with name " + name + " is not founded");
        }
    }

    public State get(Integer id) throws NotFoundException {
        try {
            return stateService.getAll().values().stream().filter(s -> s.getStateId().equals(id))
                    .findFirst().orElseThrow(NotFoundException::new);
        } catch (NotFoundException e) {
            stateService.evictStatesCache();
            return stateService.getAll().values().stream().filter(s -> s.getStateId().equals(id))
                    .findFirst().orElseThrow(() ->
                            new NotFoundException("State with id " + id.toString() + " is not founded"));
        }
    }

    public void updateState(List<Meetup> meetups, State state) {
        for (Meetup m : meetups) updateState(m, state);
    }

    public void updateState(Meetup meetups, State state) {
        meetups.setStateId(state.getStateId());
        meetupDAO.update(meetups);
    }

    public void cancelFutureMeetupsOf(AppUser user) throws NotFoundException {
        updateState(meetupDAO.futureScheduledAndBookedMeetupsOf(user), get(State.CANCELED));
    }

    public void terminateCurrentMeetupsOf(AppUser user) throws NotFoundException {
        updateState(meetupDAO.currentMeetupsOf(user), get(State.TERMINATED));
    }

    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 1500)
    public void cancelNotEnoughAttendees() throws NotFoundException {
        updateState(meetupDAO.notEnoughAttendees1Hour(), get(State.CANCELED));
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void updateInProgress() throws NotFoundException {
        updateState(meetupDAO.goingToStart(), get(State.IN_PROGRESS));
    }

    @Scheduled(fixedRate = 1000 * 60 * 5, initialDelay = 500)
    public void updatePassed() throws NotFoundException {
        updateState(meetupDAO.goingToFinish(), get(State.PASSED));
    }
}
