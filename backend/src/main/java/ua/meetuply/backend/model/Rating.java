package ua.meetuply.backend.model;

import java.time.LocalDateTime;

public class Rating {
    private int value;
    private LocalDateTime date;
    private AppUser ratedUser;
    private AppUser ratedBy;

    public Rating(){}

    public Rating(int value, LocalDateTime date, AppUser ratedUser, AppUser ratedBy){
        this.value=value;
        this.date = date;
        this.ratedUser=ratedUser;
        this.ratedBy=ratedBy;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setRatedBy(AppUser ratedBy) {
        this.ratedBy = ratedBy;
    }

    public AppUser getRatedBy() {
        return ratedBy;
    }

    public void setRatedUser(AppUser ratedUser) {
        this.ratedUser = ratedUser;
    }

    public AppUser getRatedUser() {
        return ratedUser;
    }
}
