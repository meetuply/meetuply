package ua.meetuply.backend.formbean;


import ua.meetuply.backend.model.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeetupForm {
    private Integer meetupId;
    private String meetupPlace;
    private String meetupTitle;
    private int meetupRegisteredAttendees;
    private int meetupMinAttendees;
    private int meetupMaxAttendees;
    private String meetupStartDateTime;
    private String meetupFinishDateTime;
    private Integer stateId;
    private Integer speakerId;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MeetupForm() {
    }

    //todo add userId function
    public MeetupForm(Integer meetupId, String meetupPlace, String meetupTitle,int meetupMinAttendees,
                      int meetupMaxAttendees, String meetupStartDateTime,
                      String meetupFinishDateTime, Integer stateId, Integer speakerId) {
        this.meetupId = meetupId;
        this.meetupPlace = meetupPlace;
        this.meetupTitle = meetupTitle;
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

    public String getMeetupStartDateTime() {
        return meetupStartDateTime;
    }

    public void setMeetupStartDateTime(String meetupStartDateTime) {
        this.meetupStartDateTime = meetupStartDateTime;
    }

    public String getMeetupFinishDateTime() {
        return meetupFinishDateTime;
    }

    public void setMeetupFinishDateTime(String meetupFinishDateTime) {
        this.meetupFinishDateTime = meetupFinishDateTime;
    }

    public Integer getState() {
        return stateId;
    }

    public void setState(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Integer speakerId) {
        this.speakerId = speakerId;
    }
}