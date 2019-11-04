package ua.meetuply.backend.controller.exception;

public class MeetupStateException extends Exception {
    String reason;

    public static MeetupStateException createWith(String reason) {
        return new MeetupStateException(reason);
    }
    private MeetupStateException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return "Cannot change state: '" + reason + "'";
    }
}