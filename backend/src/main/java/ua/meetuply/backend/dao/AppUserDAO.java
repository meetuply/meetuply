package ua.meetuply.backend.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
 
@Repository
public class AppUserDAO {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // Config in WebSecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    public final static AppUserDAO instance = new AppUserDAO();

    public AppUser findAppUserByEmail(String email) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email }, AppUser.userMapper);
        return users.size() == 0 ? null : users.get(0);
    }

    public List<AppUser> getAppUsers() {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user", AppUser.userMapper);
        return users;
    }

    public AppUser createAppUser(AppUserForm form) {;
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
 
        AppUser user = new AppUser(null, form.getEmail(),
                form.getFirstName(), form.getLastName(), RoleDAO.instance.getRoleByName("user"), false,
                encrytedPassword);

        addAppUserDB(user);
        return user;
    }


    public void addAppUserDB(AppUser user) {
        jdbcTemplate.update(
                // TODO role_id
                "INSERT INTO `heroku_eac346ff75652cf`.`user` (`email`, `password`, `firstname`, `surname`, `registration_confirmed`, `is_deactivated`, `allow_notifications`, `role_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getEmail(), user.getEncrytedPassword(), user.getFirstName(), user.getLastName(), 0, 0, 1, user.getRole().getRoleId()
        );
    }
 
}

