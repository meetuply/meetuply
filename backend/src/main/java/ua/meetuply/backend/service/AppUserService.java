package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.dao.RoleDAO;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;

import java.util.List;

@Component
public class AppUserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AppUserDAO appUserDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser createAppUser(AppUserForm form) {
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
        Role role = form.getRole();
        if (role != null) {
            AppUser appUser = new AppUser(null, form.getEmail(),
                    form.getFirstName(), form.getLastName(), form.getRole(),
                    false,
                    encrytedPassword);
            appUserDAO.save(appUser);
            return appUser;
        } else {
            Role userRole = roleDAO.getRoleByName("user");
            AppUser appUser = new AppUser(null, form.getEmail(),
                    form.getFirstName(), form.getLastName(), userRole,
                    false,
                    encrytedPassword);
            appUserDAO.save(appUser);
            return appUser;
        }
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
