package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppUserDAO implements IDAO<AppUser>, RowMapper<AppUser> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    RoleDAO roleDAO;

    public List<AppUser> getMeetupAttendees(Integer id) {
        List<AppUser> attendeesList = jdbcTemplate.query("select * from user where uid in (select user_id from meetup_attendees where meetup_id = ?)", new Object[]{id}, this);
        return attendeesList;
    }

    public AppUser findAppUserByEmail(String email) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[]{email}, this);
        return users.size() == 0 ? null : users.get(0);
    }

    public List<AppUser> getAppUsers() {
        return jdbcTemplate.query("SELECT * FROM user", this);
    }


    public List<AppUser> getUsersChunk(Integer startRow, Integer endRow) {
        return jdbcTemplate.query("SELECT * FROM user order by uid asc LIMIT ?, ?", new Object[]{startRow, endRow}, this);
    }

    @Override
    public AppUser get(Integer id) {
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE uid = ?", new Object[]{id}, this);
        return users.size() == 0 ? null : users.get(0);
    }

    public Integer getUserIdByEmail(String email) {
        System.out.println("IN DAO   " + jdbcTemplate.queryForObject("SELECT uid FROM user WHERE email = ?", new Object[]{email}, Integer.class));
        Integer userId = jdbcTemplate.queryForObject("SELECT uid FROM user WHERE email = ?", new Object[]{email}, Integer.class);
        return userId == null ? -1 : userId;
    }


    public Integer getUserIdByName(String firstname){
        Integer userId = jdbcTemplate.queryForObject("SELECT uid FROM user WHERE firstname = ?", new Object[] { firstname }, Integer.class);
        return userId == null ? -1 : userId;
    }


    public AppUser getUserByEmail(String email){
        List<AppUser> users = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", new Object[] { email }, this);
        return users.size() == 0 ? null : users.get(0);
    }

    public List<Integer> getUserSubscribers(Integer id) {
        return jdbcTemplate.queryForList("SELECT follower_id FROM followers WHERE followed_user_id = ?", new Object[]{id}, Integer.class);
    }


    @Override
    public List<AppUser> getAll() {
        return null;
    }

    @Override
    public void save(AppUser user) {
        jdbcTemplate.update(
                // TODO role_id
                "INSERT INTO `user` (`email`, `password`, `firstname`, `surname`, `registration_confirmed`, `is_deactivated`, `allow_notifications`, `role_id`, `description`, `location`,`photo`) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), 0, 0, 1, user.getRole().getRoleId(), user.getDescription(), user.getLocation(), user.getPhoto()
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
                        "role_id = ?, " +
                        "description = ?, " +
                        "location = ?, " +
                        "photo = ? WHERE uid = ?",
                appUser.getEmail(), appUser.getPassword(), appUser.getFirstName(),
                appUser.getLastName(), appUser.isRegistration_confirmed(),
                appUser.isDeactivated(), appUser.isAllow_notifications(), appUser.getRole().getRoleId(),
                appUser.getDescription(), appUser.getLocation(),appUser.getPhoto(),appUser.getUserId());

    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM user WHERE uid = ?", id);
    }

    public Integer getFollowersNumber(Integer id){
        Integer followersNumber = jdbcTemplate.queryForObject("select count(*) from followers where followed_user_id = ?;",
                new Object []{id}, Integer.class);
        return followersNumber != null ? followersNumber : 0;
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
                resultSet.getString("password"),
                resultSet.getString("description"),
                resultSet.getString("location"),
                resultSet.getString("photo")

        );
        return appUser;
    }


}