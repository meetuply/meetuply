package ua.meetuply.backend.model;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
public class AppUser {

    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean deactivated;
    private boolean registration_confirmed;
    private boolean allow_notifications;
    private String password;
    private String description;
    private String location;
    private String photo;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}