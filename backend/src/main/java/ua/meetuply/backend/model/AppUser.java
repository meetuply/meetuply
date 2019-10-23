package ua.meetuply.backend.model;

import lombok.*;

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

    public AppUser(Integer userId, String email, String firstName, String lastName, Role role,
                   boolean deactivated, boolean registration_confirmed, boolean allow_notifications,
                   String password, String photo) {
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
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}