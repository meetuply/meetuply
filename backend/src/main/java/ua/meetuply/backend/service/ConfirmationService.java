package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.ConfirmationTokenDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.ConfirmationToken;

@Component
public class ConfirmationService {

    @Autowired
    ConfirmationTokenDAO confirmationTokenDAO;

    @Autowired
    AppUserService appUserService;

    public ConfirmationToken generateToken(AppUser user) {
        ConfirmationToken ct = new ConfirmationToken(user);
        confirmationTokenDAO.save(ct);
        return ct;
    }

    public AppUser confirmUser(String token) {
        ConfirmationToken ct = confirmationTokenDAO.getByToken(token);
        if (ct != null) {
            appUserService.activateUser(ct.getUser());
            confirmationTokenDAO.delete(ct.getTokenid());
            return ct.getUser();
        }
        return null;
    }

    public String getConfirmLink(ConfirmationToken ct) {
        String hostName = System.getenv("HOST_NAME");
        if (hostName == null) hostName = "localhost:4200";
        return "http://" + hostName + "/#/confirm?token=" + ct.getConfirmationToken();
    }

    public String getRecoveryLink(ConfirmationToken ct) {
        String hostName = System.getenv("HOST_NAME");
        if (hostName == null) hostName = "localhost:4200";
        return "http://" + hostName + "/#/password?token=" + ct.getConfirmationToken();
    }

    public boolean exists(String token){
        return confirmationTokenDAO.getByToken(token) != null;
    }
}
