package ua.meetuply.backend.controller.exception;

public class NotFoundException extends Exception {
    private String reason;
    public NotFoundException(String reason) {
        this.reason = reason;
    }
    public NotFoundException() {
        this.reason = "Not found exception";
    }

    @Override
    public String getMessage() {
        return reason;
    }
}
