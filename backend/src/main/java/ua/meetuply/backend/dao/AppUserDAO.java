package ua.meetuply.backend.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
 
@Repository
public class AppUserDAO {
 
    // Config in WebSecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    private static final Map<Long, AppUser> USERS_MAP = new HashMap<>();
 
    static {
        initDATA();
    }
 
    private static void initDATA() {
        String encrytedPassword = "";
 
        AppUser tom = new AppUser(1L, "tom@waltdisney.com", "Tom", "HdjsKGh", Role.USER,
                false, encrytedPassword);
 
        AppUser jerry = new AppUser(2L, "jerry@waltdisney.com", "Jerry", "JHKGSFDherry", Role.SPEAKER,
                false, encrytedPassword);
 
        USERS_MAP.put(tom.getUserId(), tom);
        USERS_MAP.put(jerry.getUserId(), jerry);
    }
 
    public Long getMaxUserId() {
        long max = 0;
        for (Long id : USERS_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }
 
    public static AppUser findAppUserByEmail(String email) {
        Collection<AppUser> appUsers = USERS_MAP.values();
        for (AppUser u : appUsers) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
 
    public static List<AppUser> getAppUsers() {
        List<AppUser> list = new ArrayList<>();
 
        list.addAll(USERS_MAP.values());
        return list;
    }
 
    public AppUser createAppUser(AppUserForm form) {
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
 
        AppUser user = new AppUser(userId, form.getEmail(),
                form.getFirstName(), form.getLastName(), form.getRole(), false,
                encrytedPassword);
 
        USERS_MAP.put(userId, user);
        return user;
    }
 
}

