package ua.meetuply.backend.controller.exception;

public class NotFoundException extends Exception {
    private String reason;
    public static NotFoundException createWith(String reason) {
        return new NotFoundException(reason);
    }
    private NotFoundException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
