package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.dao.RoleDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;

import java.util.List;

@Component
public class AppUserService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AppUserDAO appUserDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAppUser(AppUser appUser) {
        String encrytedPassword = this.passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encrytedPassword);
        appUser.setConfirmedPassword("");
        Role role = appUser.getRole();
        if (role == null) {
            Role userRole = roleDAO.getRoleByName("user");
            appUser.setRole(userRole);
        }
        appUserDAO.save(appUser);
    }

    public List<AppUser> getAppUsers() {
        return appUserDAO.getAppUsers();
    }

    public Integer getUserIdByEmail(String email){
        return appUserDAO.getUserIdByEmail(email);
    }

    public AppUser getUser(Integer id){
        return appUserDAO.get(id);
    }

    public int getCurrentUserID() {
        String email = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        int userId = appUserDAO.getUserIdByEmail(email);
        return userId;
    }
}
