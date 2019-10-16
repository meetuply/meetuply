package ua.meetuply.backend.model;

import java.util.Date;
import java.util.UUID;

public class ConfirmationToken {

    private Integer tokenid;
    private String confirmationToken;
    private Date createdDate;
    private AppUser user;

    public ConfirmationToken(Integer tokenid, String confirmationToken, Date createdDate, AppUser user) {
        this.tokenid = tokenid;
        this.confirmationToken = confirmationToken;
        this.createdDate = createdDate;
        this.user = user;
    }

    public ConfirmationToken(AppUser user) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public AppUser getUser() {
        return user;
    }

    public Integer getTokenid() {
        return tokenid;
    }
}
