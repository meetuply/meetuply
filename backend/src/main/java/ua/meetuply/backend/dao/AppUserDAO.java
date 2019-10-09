package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.formbean.AppUserForm;
import ua.meetuply.backend.model.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
@Repository
public class AppUserDAO implements RowMapper<AppUser> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // Config in WebSecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDAO roleDAO;

    public AppUser findAppUserByEmail(String email) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email }, this);
        return users.size() == 0 ? null : users.get(0);
    }

    public List<AppUser> getAppUsers() {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user", this);
        return users;
    }

    public AppUser createAppUser(AppUserForm form) {
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
 
        AppUser user = new AppUser(null, form.getEmail(),
                form.getFirstName(), form.getLastName(), roleDAO.getRoleByName("user"), false,
                encrytedPassword);

        addAppUserDB(user);
        return user;
    }


    public void addAppUserDB(AppUser user) {
        jdbcTemplate.update(
                // TODO role_id
                "INSERT INTO `user` (`email`, `password`, `firstname`, `surname`, `registration_confirmed`, `is_deactivated`, `allow_notifications`, `role_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getEmail(), user.getEncrytedPassword(), user.getFirstName(), user.getLastName(), 0, 0, 1, user.getRole().getRoleId()
        );
        //user.getRole().getRoleId()
    }

    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AppUser(
                resultSet.getInt("uid"),
                resultSet.getString("email"),
                resultSet.getString("firstname"),
                resultSet.getString("surname"),
                roleDAO.getRoleById(resultSet.getInt("role_id")),
                resultSet.getBoolean("is_deactivated"),
                resultSet.getString("password")
        );
    }
 
}

