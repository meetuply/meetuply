package ua.meetuply.backend.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class State {

    private long stateId;
    private String name;
    Set<Meetup> meetupSet = new HashSet<>();

    public State() {
    }

    public State(int id, String name) {
        this.stateId = id;
        this.name = name;
    }

    public long getId() {
        return stateId;
    }

    public void setId(int id) {
        this.stateId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Meetup> getMeetupSet() {
        return meetupSet;
    }

    public void setMeetupSet(Set<Meetup> meetupSet) {
        this.meetupSet = meetupSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return getId() == state.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "State{" +
                "stateId=" + stateId +
                ", name='" + name + '\'' +
                ", meetupSet=" + meetupSet +
                '}';
    }
}