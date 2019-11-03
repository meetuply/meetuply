package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MeetupItem {
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
    private String speakerFirstName;
    private String speakerLastName;
    private Float rating;
}