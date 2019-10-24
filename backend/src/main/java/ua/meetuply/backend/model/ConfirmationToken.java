package ua.meetuply.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ConfirmationToken {

    private Integer tokenid;
    private String confirmationToken;
    private Date createdDate;
    private AppUser user;

    public ConfirmationToken(AppUser user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
