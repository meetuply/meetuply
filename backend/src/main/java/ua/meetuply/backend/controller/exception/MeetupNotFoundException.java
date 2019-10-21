package ua.meetuply.backend.controller.exception;

public class MeetupNotFoundException extends Exception {
    private Integer id;

    public static MeetupNotFoundException createWith(Integer id) {
        return new MeetupNotFoundException(id);
    }

    private MeetupNotFoundException(Integer id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Meetup '" + id + "' not found";
    }
}