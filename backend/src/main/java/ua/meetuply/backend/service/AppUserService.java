package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.dao.RoleDAO;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AppUserService implements UserDetailsService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired @Lazy
    AppUserDAO appUserDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAppUser(AppUser appUser) {
        String encrytedPassword = this.passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encrytedPassword);
        appUser.setDeactivated(false);
        appUser.setAllow_notifications(true);
        appUser.setRegistration_confirmed(false);
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

    public List<AppUser> getMeetupAttendees(Integer meetupId) {return appUserDAO.getMeetupAttendees(meetupId);}

    public Integer getUserIdByEmail(String email){
        return appUserDAO.getUserIdByEmail(email);
    }

    public AppUser getUser(Integer id){
        return appUserDAO.get(id);
    }

    public String getUserFullName(Integer id){
        AppUser user =  appUserDAO.get(id);
        return user.getFirstName() + " " + user.getLastName();
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

    public AppUser getCurrentUser() {
        String email = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return appUserDAO.getUserByEmail(email);
    }

    public AppUser getUserByEmail(String email){
        return appUserDAO.getUserByEmail(email);
    }

    public void activateUser(AppUser user) {
        user.setRegistration_confirmed(true);
        appUserDAO.update(user);
    }

    public void deactivateUser(AppUser user) {
        user.setDeactivated(true);
        appUserDAO.update(user);
    }

    public void activateDeactivatedUser(AppUser user) {
        user.setDeactivated(false);
        appUserDAO.update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser appUser = appUserDAO.findAppUserByEmail(email);
        if (appUser == null) throw new UsernameNotFoundException(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User user;
        if(appUser.getRole().getRoleName().equals("admin"))
            user = new User(appUser.getEmail(), appUser.getPassword(), grantedAuthorities);
        else user = new User(appUser.getEmail(), appUser.getPassword(), new HashSet<>());
        return user;
    }
}
