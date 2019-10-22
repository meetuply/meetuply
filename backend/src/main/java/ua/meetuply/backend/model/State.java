package ua.meetuply.backend.model;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
public class State {

    private long stateId;
    private String name;

    Set<Meetup> meetupSet = new HashSet<>();

    public State(int id, String name) {
        this.stateId = id;
        this.name = name;
    }
}