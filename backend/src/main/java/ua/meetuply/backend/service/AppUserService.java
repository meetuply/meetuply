package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    RoleDAO roleDao;

    @Autowired
    AppUserDAO appUserDao;

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
            appUserDao.save(appUser);
            return appUser;
        } else {
            Role userRole = roleDao.getRoleByName("user");
            AppUser appUser = new AppUser(null, form.getEmail(),
                    form.getFirstName(), form.getLastName(), userRole,
                    false,
                    encrytedPassword);
            appUserDao.save(appUser);
            return appUser;
        }
    }

    public List<AppUser> getAppUsers() {
        return appUserDao.getAppUsers();
    }

    public Integer getUserIdByEmail(String email){
        return appUserDao.getUserIdByEmail(email);
    }
}