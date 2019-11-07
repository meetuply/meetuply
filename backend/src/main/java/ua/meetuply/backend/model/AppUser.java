package ua.meetuply.backend.model;

import lombok.*;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
public class AppUser {

    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean deactivated;
    private String password;
    private String photo;
    private boolean registration_confirmed;
    private boolean allow_notifications;
    private String description;
    private String location;



    public AppUser(Integer userId, String email, String firstName, String lastName, Role role, boolean deactivated, boolean registration_confirmed, boolean allow_notifications, String password, String description,String location,String photo) {

        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.deactivated = deactivated;
        this.password = password;
        this.registration_confirmed = registration_confirmed;
        this.allow_notifications = allow_notifications;
        this.photo = photo;
        this.description = description;
        this.location = location;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRegistration_confirmed() {
        return registration_confirmed;
    }

    public void setRegistration_confirmed(boolean registration_confirmed) {
        this.registration_confirmed = registration_confirmed;
    }

    public boolean isAllow_notifications() {
        return allow_notifications;
    }

    public void setAllow_notifications(boolean allow_notifications) {
        this.allow_notifications = allow_notifications;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}