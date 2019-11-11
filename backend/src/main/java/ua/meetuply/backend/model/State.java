package ua.meetuply.backend.model;

public interface State {
    String BOOKED = "Booked";
    String CANCELED = "Canceled";
    String IN_PROGRESS = "In progress";
    String PASSED = "Passed";
    String SCHEDULED = "Scheduled";
    String TERMINATED = "Terminated";

    Integer getStateId();
    String getName();

}
