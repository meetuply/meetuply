package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.AppUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppUserDAO implements IDAO<AppUser>, RowMapper<AppUser> {

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM user";
    private static final String SELECT_USER_BY_EMAIL_QUERY =
            "SELECT * FROM user WHERE email = ?";
    private static final String SELECT_USER_ID_BY_EMAIL_QUERY =
            "SELECT uid FROM user WHERE email = ?";
    private static final String SELECT_USER_BY_ID =
            "SELECT * FROM user WHERE uid = ?";
    private static final String SELECT_CHUNK_OF_USERS =
            "SELECT * FROM user WHERE registration_confirmed=1 order by uid asc LIMIT ?, ?";
    private static final String SELECT_CHUNK_OF_USERS_BY_NAME =
            "SELECT *, CONCAT(firstname, ' ', surname) AS full_name FROM user WHERE is_deactivated=0 AND registration_confirmed=1 " +
                    "HAVING LOWER(full_name) LIKE CONCAT('%',?,'%') ORDER BY uid asc LIMIT ?, ?";
    private static final String SELECT_CHUNK_OF_SPEAKERS =
            "SELECT * FROM user WHERE is_deactivated=0 AND registration_confirmed=1 AND uid IN " +
                    "(SELECT speaker_id FROM meetup UNION SELECT author_id FROM post) " +
                    "ORDER BY uid LIMIT ?, ?";
    private static final String SELECT_CHUNK_OF_SPEAKERS_BY_NAME =
            "SELECT *, CONCAT(firstname, ' ', surname) AS full_name FROM user WHERE is_deactivated=0 AND registration_confirmed=1 AND uid IN " +
                    "(SELECT speaker_id FROM meetup UNION SELECT author_id FROM post) " +
                    "HAVING LOWER(full_name) LIKE CONCAT('%',?,'%') ORDER BY uid asc LIMIT ?, ?";
    private static final String SELECT_MEETUP_ATTENDEES_QUERY =
            "SELECT * FROM user WHERE uid IN (SELECT user_id FROM meetup_attendees WHERE meetup_id = ?)";
    private static final String SELECT_FOLLOWERS_IDS_OF_USER_QUERY =
            "SELECT follower_id FROM followers WHERE followed_user_id = ?";
    private static final String SELECT_FOLLOWED_USERS_IDS_OF_USER_QUERY =
            "SELECT followed_user_id FROM followers WHERE follower_id = ?";
    private static final String SELECT_FOLLOWED_USERS_OF_USER_QUERY =
            "SELECT * FROM user WHERE uid IN (SELECT followed_user_id FROM followers WHERE follower_id = ?)";
    private static final String SELECT_FOLLOWER_NUMBER_QUERY =
            "select count(*) from followers where followed_user_id = ?";

    private static final String INSERT_INTO_FOLLOWERS_QUERY =
            "INSERT INTO followers (`follower_id`, `followed_user_id`) VALUES (?, ?)";
    private static final String INSERT_USER_QUERY =
            "INSERT INTO `user` (`email`, `password`, `firstname`, `surname`, `registration_confirmed`, `is_deactivated`, `allow_notifications`, `role_id`, `description`, `location`,`photo`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_USER_QUERY =
            "UPDATE user " +
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
                    "photo = ? WHERE uid = ?";
    private static final String UPDATE_PASSWORD_QUERY =
            "UPDATE user SET password = ? WHERE uid = ?";

    private static final String DELETE_FROM_FOLLOWERS_QUERY =
            "DELETE FROM followers WHERE follower_id = ? AND followed_user_id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM user WHERE uid = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleDAO roleDAO;

    public List<AppUser> getMeetupAttendees(Integer id) {
        return jdbcTemplate.query(SELECT_MEETUP_ATTENDEES_QUERY, new Object[]{id}, this);
    }

    public List<AppUser> getSpeakersChunk(Integer startRow, Integer endRow) {
        return jdbcTemplate.query(SELECT_CHUNK_OF_SPEAKERS, new Object[]{startRow, endRow}, this);
    }

    public List<AppUser> getUsersChunk(Integer startRow, Integer endRow) {
        return jdbcTemplate.query(SELECT_CHUNK_OF_USERS, new Object[]{startRow, endRow}, this);
    }

    public List<AppUser> getSpeakersChunkByName(Integer startRow, Integer endRow, String name) {
        return jdbcTemplate.query(SELECT_CHUNK_OF_SPEAKERS_BY_NAME, new Object[]{name, startRow, endRow}, this);
    }

    public List<AppUser> getUsersChunkByName(Integer startRow, Integer endRow, String name) {
        return jdbcTemplate.query(SELECT_CHUNK_OF_USERS_BY_NAME, new Object[]{name, startRow, endRow}, this);
    }

    public Integer getUserIdByEmail(String email) {
        Integer userId = jdbcTemplate.queryForObject(SELECT_USER_ID_BY_EMAIL_QUERY, new Object[]{email}, Integer.class);
        return userId == null ? -1 : userId;
    }

    public AppUser getUserByEmail(String email) {
        return jdbcTemplate.query(SELECT_USER_BY_EMAIL_QUERY, new Object[]{email}, this).stream().findFirst().orElse(null);
    }

    public List<Integer> getFollowersIdsOfUser(Integer id) {
        return jdbcTemplate.queryForList(SELECT_FOLLOWERS_IDS_OF_USER_QUERY, new Object[]{id}, Integer.class);
    }

    public List<Integer> getFollowedUsersIdsOfUser(Integer id) {
        return jdbcTemplate.queryForList(SELECT_FOLLOWED_USERS_IDS_OF_USER_QUERY, new Object[]{id}, Integer.class);
    }

    public List<AppUser> getFollowedUsersOfUser(Integer id) {
        return jdbcTemplate.query(SELECT_FOLLOWED_USERS_OF_USER_QUERY, new Object[]{id}, this);
    }

    public void follow(Integer currentUserID, Integer userId) {
        jdbcTemplate.update(INSERT_INTO_FOLLOWERS_QUERY, currentUserID, userId);
    }

    public void unfollow(int currentUserID, Integer userId) {
        jdbcTemplate.update(DELETE_FROM_FOLLOWERS_QUERY, currentUserID, userId);
    }

    public void changePassword(AppUser appUser) {
        jdbcTemplate.update(UPDATE_PASSWORD_QUERY,
                appUser.getPassword(), appUser.getUserId());
    }

    public Integer getFollowersNumber(Integer id) {
        Integer followersNumber = jdbcTemplate.queryForObject(SELECT_FOLLOWER_NUMBER_QUERY,
                new Object[]{id}, Integer.class);
        return followersNumber != null ? followersNumber : 0;
    }

    @Override
    public AppUser get(Integer id) {
        return jdbcTemplate.query(SELECT_USER_BY_ID, new Object[]{id}, this).stream().findFirst().orElse(null);
    }

    @Override
    public List<AppUser> getAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this);
    }

    @Override
    public void save(AppUser user) {
        jdbcTemplate.update(INSERT_USER_QUERY,
                user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), 0, 0, 1, user.getRole().getRoleId(), user.getDescription(), user.getLocation(), user.getPhoto()
        );
    }

    @Override
    public void update(AppUser appUser) {
        jdbcTemplate.update(UPDATE_USER_QUERY,
                appUser.getEmail(), appUser.getPassword(), appUser.getFirstName(),
                appUser.getLastName(), appUser.isRegistration_confirmed(),
                appUser.isDeactivated(), appUser.isAllow_notifications(), appUser.getRole().getRoleId(),
                appUser.getDescription(), appUser.getLocation(), appUser.getPhoto(), appUser.getUserId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_USER_QUERY, id);
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