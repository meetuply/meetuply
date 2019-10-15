package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.AppUser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppUserDAO implements IDAO<AppUser>, RowMapper<AppUser> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    // Config in WebSecurityConfig
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleDAO roleDAO;

    public AppUser findAppUserByEmail(String email) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email }, this);
        return users.size() == 0 ? null : users.get(0);
    }
    public List<AppUser> getAppUsers() {
        return jdbcTemplate.query("SELECT * FROM user", this);
    }

    @Override
    public AppUser get(Integer id) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE uid = ?", new Object[] { id }, this);
        return users.size() == 0 ? null : users.get(0);
    }

    public Integer getUserIdByEmail(String email){
        System.out.println("IN DAO   "+jdbcTemplate.queryForObject("SELECT uid FROM user WHERE email = ?", new Object[] { email }, Integer.class));
        Integer userId = jdbcTemplate.queryForObject("SELECT uid FROM user WHERE email = ?", new Object[] { email }, Integer.class);
        return userId == null ? -1 : userId;
    }

    public AppUser getUserByEmail(String email){
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email }, this);
        return users.size() == 0 ? null : users.get(0);
    }

    @Override
    public List<AppUser> getAll() {
        return null;
    }

    @Override
    public void save(AppUser user) {
        jdbcTemplate.update(
                // TODO role_id
                "INSERT INTO `user` (`email`, `password`, `firstname`, `surname`, `registration_confirmed`, `is_deactivated`, `allow_notifications`, `role_id`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), 0, 0, 1, user.getRole().getRoleId()
        );
    }

    @Override
    public void update(AppUser appUser) {
        jdbcTemplate.update("UPDATE user " +
                        "SET email = ?, " +
                        "password = ?, " +
                        "firstname = ?, " +
                        "surname = ?, " +
                        "registration_confirmed = ?, " +
                        "is_deactivated = ?, " +
                        "allow_notifications = ?, " +
                        "role_id = ? WHERE uid = ?",
                appUser.getEmail(), appUser.getPassword(), appUser.getFirstName(),
                appUser.getLastName(), appUser.isRegistration_confirmed(),
                appUser.isDeactivated(), appUser.isAllow_notifications(), appUser.getRole().getRoleId(),
                appUser.getUserId());
    }

    @Override
    public void delete(Integer id) {

    }

    public AppUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        AppUser appUser = new AppUser(
                resultSet.getInt("uid"),
                resultSet.getString("email"),
                resultSet.getString("firstname"),
                resultSet.getString("surname"),
                roleDAO.get(resultSet.getInt("role_id")),
                resultSet.getBoolean("is_deactivated"),
                resultSet.getBoolean("registration_confirmed"),
                resultSet.getBoolean("allow_notifications"),
                resultSet.getString("password")
        );
        return appUser;
    }
}