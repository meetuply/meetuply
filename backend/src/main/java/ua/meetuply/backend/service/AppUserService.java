package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.controller.exception.PermissionException;
import ua.meetuply.backend.dao.AppUserDAO;
import ua.meetuply.backend.dao.ConfirmationTokenDAO;
import ua.meetuply.backend.dao.RoleDAO;
import ua.meetuply.backend.model.AchievementType;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.ConfirmationToken;
import ua.meetuply.backend.model.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AppUserService implements UserDetailsService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    AppUserDAO appUserDAO;

    @Autowired
    SessionService sessionService;

    @Autowired
    StateService stateService;

    @Autowired
    ConfirmationTokenDAO confirmationTokenDAO;

    @Autowired
    AchievementService achievementService;

    @Autowired
    NotificationService notificationService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void createAppUser(AppUser appUser) {
        String encrytedPassword = this.passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encrytedPassword);
        appUser.setDeactivated(false);
        appUser.setAllow_notifications(true);
        appUser.setPhoto("");
        appUser.setLocation("");
        appUser.setDescription("");
        appUser.setRegistration_confirmed(false);
        Role role = appUser.getRole();
        if (role == null) {
            Role userRole = roleDAO.getRoleByName("user");
            appUser.setRole(userRole);
        }
        appUserDAO.save(appUser);
    }

    public List<AppUser> getAppUsers() {
        return appUserDAO.getAll();
    }

    public List<AppUser> getUsersChunk(Integer startRow, Integer endRow) {
        return appUserDAO.getSpeakersChunk(startRow, endRow);
    }

    public List<AppUser> getUsersChunkByName(Integer startRow, Integer endRow, String name) {
        return appUserDAO.getSpeakersChunkByName(startRow, endRow, name);
    }

    public List<AppUser> getUsersChunkForAdmin(Integer startRow, Integer endRow) {
        return appUserDAO.getUsersChunk(startRow, endRow);
    }

    public List<AppUser> getUsersChunkForAdminByName(Integer startRow, Integer endRow, String name) {
        return appUserDAO.getUsersChunkByName(startRow, endRow, name);
    }

    public List<AppUser> getMeetupAttendees(Integer meetupId) {
        return appUserDAO.getMeetupAttendees(meetupId);
    }

    public List<Integer> getUserSubscribers(Integer id) {
        return appUserDAO.getFollowersIdsOfUser(id);
    }

    public List<Integer> getUserSubscriptions(Integer id) {
        return appUserDAO.getFollowedUsersIdsOfUser(id);
    }

    public List<AppUser> getUserSubscriptionsUsers(Integer id) {
        return appUserDAO.getFollowedUsersOfUser(id);
    }

    public AppUser getUser(Integer id) {
        return appUserDAO.get(id);
    }

    public String getUserFullName(Integer id) {
        AppUser user = appUserDAO.get(id);
        return user.getFirstName() + " " + user.getLastName();
    }

    public int getCurrentUserID() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return appUserDAO.getUserIdByEmail(email);
    }

    public AppUser getCurrentUser() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return appUserDAO.getUserByEmail(email);
    }

    public AppUser getUserByEmail(String email) {
        return appUserDAO.getUserByEmail(email);
    }

    public void activateUser(AppUser user) {
        user.setRegistration_confirmed(true);
        appUserDAO.update(user);
    }

    public void deactivateUser(AppUser user) throws NotFoundException {
        user.setDeactivated(true);
        appUserDAO.update(user);
        sessionService.expireUserSessions(user.getEmail());
        stateService.terminateCurrentMeetupsOf(user);
        stateService.cancelFutureMeetupsOf(user);
    }

    public void activateDeactivatedUser(AppUser user) {
        user.setDeactivated(false);
        appUserDAO.update(user);
    }

    public boolean isAdmin() {
        return getCurrentUser().getRole().equals(roleDAO.getRoleByName("admin"));
    }

    @Transactional
    public void follow(Integer userId) {
        appUserDAO.follow(getCurrentUserID(), userId);
        achievementService.checkOne(AchievementType.FOLLOWERS, userId);
        notificationService.sendNotification(userId,"new_subscriber_template");
        notificationService.sendNotification(getCurrentUserID(),"new_subscription_template");
    }

    public void unfollow(Integer userId) {
        appUserDAO.unfollow(getCurrentUserID(), userId);
    }

    public Integer getFollowersNumber(Integer userId) {
        return appUserDAO.getFollowersNumber(userId);
    }

    public void update(AppUser appUser) throws Exception {
        if (getCurrentUserID() == appUser.getUserId()) {
            appUser.setPassword(appUser.getPassword() == null ? getCurrentUser().getPassword() : passwordEncoder.encode(appUser.getPassword()));
            appUserDAO.update(appUser);
        } else throw PermissionException.createWith("You don't have permission to change personal data");
    }

    public AppUser update(AppUser appUser, String token) throws Exception {
        if (token != null) {
            ConfirmationToken ct = confirmationTokenDAO.getByToken(token);
            appUser.setUserId(ct.getUser().getUserId());
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUserDAO.changePassword(appUser);
            confirmationTokenDAO.delete(ct.getTokenid());
            return getUser(appUser.getUserId());
        } else throw new NotFoundException("Token can't be null");
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser appUser = appUserDAO.getUserByEmail(email);
        if (appUser == null) throw new UsernameNotFoundException(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User user;

        System.out.println(appUser);
        if (appUser.getRole().getRoleName().equals("admin")) {
            user = new User(appUser.getEmail(), appUser.getPassword(), grantedAuthorities);
        } else user = new User(appUser.getEmail(), appUser.getPassword(), new HashSet<>());
        return user;
    }



}
