package ua.meetuply.backend.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Meetup {

    private Integer meetupId;
    private String meetupPlace;
    private String meetupTitle;
    private String meetupDescription;
    private int meetupRegisteredAttendees;
    private int meetupMinAttendees;
    private int meetupMaxAttendees;
    private LocalDateTime meetupStartDateTime;
    private LocalDateTime meetupFinishDateTime;
    private Integer stateId;
    private Integer speakerId;

    public Meetup(){}

    public Meetup(Integer meetupId, String meetupPlace, String meetupTitle,
                  int meetupRegisteredAttendees,
                  int meetupMinAttendees, int meetupMaxAttendees, LocalDateTime meetupStartDateTime,
                  LocalDateTime meetupFinishDateTime, Integer stateId, Integer speakerId) {
        this.meetupId = meetupId;
        this.meetupPlace = meetupPlace;
        this.meetupTitle = meetupTitle;
//        this.meetupDescription = meetupDescription;
        this.meetupRegisteredAttendees = meetupRegisteredAttendees;
        this.meetupMinAttendees = meetupMinAttendees;
        this.meetupMaxAttendees = meetupMaxAttendees;
        this.meetupStartDateTime = meetupStartDateTime;
        this.meetupFinishDateTime = meetupFinishDateTime;
        this.stateId = stateId;
        this.speakerId = speakerId;
    }

    public Integer getMeetupId() {
        return meetupId;
    }

    public void setMeetupId(Integer meetupId) {
        this.meetupId = meetupId;
    }

    public String getMeetupPlace() {
        return meetupPlace;
    }

    public void setMeetupPlace(String meetupPlace) {
        this.meetupPlace = meetupPlace;
    }

    public String getMeetupTitle() {
        return meetupTitle;
    }

    public void setMeetupTitle(String meetupTitle) {
        this.meetupTitle = meetupTitle;
    }

    public int getMeetupRegisteredAttendees() {
        return meetupRegisteredAttendees;
    }

    public void setMeetupRegisteredAttendees(int meetupRegisteredAttendees) {
        this.meetupRegisteredAttendees = meetupRegisteredAttendees;
    }

    public int getMeetupMinAttendees() {
        return meetupMinAttendees;
    }

    public void setMeetupMinAttendees(int meetupMinAttendees) {
        this.meetupMinAttendees = meetupMinAttendees;
    }

    public int getMeetupMaxAttendees() {
        return meetupMaxAttendees;
    }

    public void setMeetupMaxAttendees(int meetupMaxAttendees) {
        this.meetupMaxAttendees = meetupMaxAttendees;
    }

    public LocalDateTime getMeetupStartDateTime() {
        return meetupStartDateTime;
    }

    public void setMeetupStartDateTime(LocalDateTime meetupStartDateTime) {
        this.meetupStartDateTime = meetupStartDateTime;
    }

    public LocalDateTime getMeetupFinishDateTime() {
        return meetupFinishDateTime;
    }

    public void setMeetupFinishDateTime(LocalDateTime meetupFinishDateTime) {
        this.meetupFinishDateTime = meetupFinishDateTime;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }

    public String getMeetupDescription() {
        return meetupDescription;
    }

    public void setMeetupDescription(String meetupDescription) {
        this.meetupDescription = meetupDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meetup)) return false;
        Meetup meetup = (Meetup) o;
        return getMeetupId() == meetup.getMeetupId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMeetupId());
    }

    @Override
    public String toString() {
        return "Meetup{" +
                "meetupId=" + meetupId +
                ", meetupPlace='" + meetupPlace + '\'' +
                ", meetupTitle='" + meetupTitle + '\'' +
                ", meetupRegisteredAttendees=" + meetupRegisteredAttendees +
                ", meetupMinAttendees=" + meetupMinAttendees +
                ", meetupMaxAttendees=" + meetupMaxAttendees +
                ", meetupStartDateTime=" + meetupStartDateTime +
                ", meetupFinishDateTime=" + meetupFinishDateTime +
                ", stateId=" + stateId +
                '}';
    }
}