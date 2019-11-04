package ua.meetuply.backend.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State {
    private Integer stateId;
    private String name;

    public enum StateNames {
        BOOKED("Booked"),
        CANCELED("Canceled"),
        IN_PROGRESS("In progress"),
        PASSED("Passed"),
        SCHEDULED("Scheduled"),
        TERMINATED("Terminated");

        public final String name;
        StateNames(String name) { this.name = name; }
    }
}