package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    AppUserService appUserService;

    public void expireUserSessions(String username) {
        for (SessionInformation si: sessionRegistry.getAllSessions(username, false)) {
            si.expireNow();
        }
    }
}