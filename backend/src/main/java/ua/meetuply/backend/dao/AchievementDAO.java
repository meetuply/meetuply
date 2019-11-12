package ua.meetuply.backend.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Achievement;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class AchievementDAO implements IDAO<Achievement>, RowMapper<Achievement> {

    private static final String GET_ONE_QUERY = "SELECT * FROM achievement WHERE uid = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM achievement";
    private static final String GET_USER_ACHIEVEMENTS_QUERY = "SELECT uid, title, description, icon," +
            "followers, posts, rating, meetups from user_achievement " +
            "inner join achievement on " +
            "user_achievement.achievement_id = achievement.uid " +
            "where user_id = ?";
    private static final String SAVE_ACHIEVEMENT_QUERY = "INSERT INTO achievement (`title`, `description`, `icon`, followers, " +
            "posts, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SAVE_ACHIEVEMENT_MEETUP_QUERY = "insert into achievement_topic (`achievement_id`, `topic_id`, `quantity`) " +
            "values (?,?,?)";
    private static final String SAVE_AND_RETURN_ID_QUERY = "INSERT INTO achievement (`title`, `description`, `icon`, followers, " +
            "posts, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE achievement SET title = ?, description = ?, " +
            "icon = ?, followers = ?, posts = ?, rating = ?, " +
            "meetups = ? WHERE uid = ?";
    private static final String DELETE_QUERY = "DELETE FROM achievement WHERE uid = ?";
    private static final String GET_MEETUP_ACHIEVEMENT_ID_TO_AWARD_QUERY = "select uid from achievement\n" +
            "where uid not in\n" +
            "(select achievement_id from user_achievement where user_id = ?)\n" +
            "and meetups <= (select count(*) from meetup where speaker_id = ?)\n" +
            "and posts is null\n" +
            "and followers is null\n" +
            "and rating is null\n" +
            "order by followers asc";
    private static final String GET_FOLLOWERS_ACHIEVEMENT_ID_TO_AWARD_QUERY = "select uid from achievement\n" +
            "where uid not in\n" +
            "(select achievement_id from user_achievement where user_id = ?)\n" +
            "and followers <= (select count(*) from followers where followed_user_id = ?)\n" +
            "and meetups is null\n" +
            "and posts is null\n" +
            "and rating is null\n" +
            "order by followers asc";
    private static final String GET_POSTS_ACHIEVEMENT_ID_TO_AWARD_QUERY = "select uid from achievement " +
            "where uid not in " +
            "(select achievement_id from user_achievement where user_id = ?)\n" +
            "and posts <= (select count(*) from post where author_id = ?)\n" +
            "and meetups is null\n" +
            "and followers is null\n" +
            "and rating is null\n" +
            "order by posts asc";
    private static final String GET_RATING_ACHIEVEMENT_ID_TO_AWARD_QUERY = "select uid from achievement\n" +
            "where uid not in\n" +
            "(select achievement_id from user_achievement where user_id = ?)\n" +
            "and rating <= (select avg(value) from rating where rated_user_id = ?)\n" +
            "and posts is null\n" +
            "and followers is null\n" +
            "and meetups is null\n" +
            "order by rating asc";
    private static final String UPDATE_ACHIEVEMENT_USER_QUERY = "insert into user_achievement (`achievement_id`, `user_id`) values (?, ?)";
    private static final String GET_ACHIEVEMENTS_ID_TO_AWARD_FOR_MEETUP_TOPIC_QUERY =
            "select achievement_id from achievement_topic\n" +
                    "where achievement_id not in (select achievement_id from user_achievement where user_id = ?)\n" +
                    "and achievement_id in (select achievement_id from achievement_topic as a\n" +
                    "inner join (select topic_id, count(meetup_id) as user_quantity from meetup_topic\n" +
                    "inner join meetup on meetup.uid = meetup_topic.meetup_id\n" +
                    "where speaker_id = ?\n" +
                    "group by topic_id) as b on a.topic_id = b.topic_id\n" +
                    "where a.quantity = b.user_quantity)\n" +
                    "group by achievement_id\n" +
                    "having sum(quantity) <= (select count(uid) from meetup where speaker_id = ?)";
    private static final String GET_USER_ACHIEVEMENTS_SUM_QUERY = "SELECT COUNT(*) FROM user_achievement WHERE user_id = ?";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public Achievement get(Integer id) {
        log.debug("Getting achievement by id");
        List<Achievement> achievements = jdbcTemplate.query(GET_ONE_QUERY, new Object[]{id}, this);
        return achievements.size() == 0 ? null : achievements.get(0);
    }

    @Override
    public List<Achievement> getAll() {
        log.debug("Getting all achievements list");
        return jdbcTemplate.query(GET_ALL_QUERY, this);
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        log.debug("Getting user achievements list");
        return jdbcTemplate.query(GET_USER_ACHIEVEMENTS_QUERY, new Object[]{userId}, this);
    }

    @Override
    public void save(Achievement achievement) {
        log.debug("Saving achievement");
        jdbcTemplate.update(SAVE_ACHIEVEMENT_QUERY,
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowers(), achievement.getPosts(), achievement.getRating(),
                achievement.getMeetups());
    }

    public void saveAchievementMeetup(Integer achievementId, Integer topicId, Integer quantity) {
        log.debug("Saving achievement for meetups with topic id {} and quantity {}", topicId, quantity);
        jdbcTemplate.update(SAVE_ACHIEVEMENT_MEETUP_QUERY,
                achievementId, topicId, quantity);
    }

    public Integer saveReturnId(Achievement achievement) {
        log.debug("Saving achievements with id return");
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_AND_RETURN_ID_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, achievement.getTitle());
            ps.setString(2, achievement.getDescription());
            ps.setString(3, achievement.getIcon());
            ps.setObject(4, achievement.getFollowers(), JDBCType.INTEGER);
            ps.setObject(5, achievement.getPosts(), JDBCType.INTEGER);
            ps.setObject(6, achievement.getRating(), JDBCType.FLOAT);
            ps.setObject(7, achievement.getMeetups(), JDBCType.INTEGER);
            return ps;
        }, keyHolder);
        BigInteger b1 = (BigInteger) Objects.requireNonNull(keyHolder.getKeys()).get("GENERATED_KEY");
        return b1.intValue();
    }

    @Override
    public void update(Achievement achievement) {
        log.debug("Updating achievement");
        jdbcTemplate.update(UPDATE_QUERY,
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowers(), achievement.getPosts(),
                achievement.getRating(), achievement.getMeetups(), achievement.getAchievementId());
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting achievement");
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<Integer> getMeetupTopicAchievementId(Integer userId) {
        log.debug("Getting achievements id list for meetups with topics if it can be awarded to the user");
        return jdbcTemplate.queryForList(GET_ACHIEVEMENTS_ID_TO_AWARD_FOR_MEETUP_TOPIC_QUERY,
                new Object[]{userId, userId, userId}, Integer.class);
    }

    public List<Integer> getMeetupAchievementId(Integer userId) {
        log.debug("Getting achievements id list for meetups if it can be awarded to the user");
        return jdbcTemplate.queryForList(GET_MEETUP_ACHIEVEMENT_ID_TO_AWARD_QUERY, new Object[]{userId, userId}, Integer.class);
    }

    public List<Integer> getFollowersAchievementId(Integer userId) {
        log.debug("Getting achievements id list for followers if it can be awarded to the current user");
        return jdbcTemplate.queryForList(GET_FOLLOWERS_ACHIEVEMENT_ID_TO_AWARD_QUERY, new Object[]{userId, userId}, Integer.class);
    }

    public List<Integer> getPostsAchievementId(Integer userId) {
        log.debug("Getting achievements id list for posts if it can be awarded to the current user");
        return jdbcTemplate.queryForList(GET_POSTS_ACHIEVEMENT_ID_TO_AWARD_QUERY, new Object[]{userId, userId}, Integer.class);
    }

    public List<Integer> getRatingAchievementId(Integer userId) {
        log.debug("Getting achievements id list for rating if it can be awarded to the current user");
        return jdbcTemplate.queryForList(GET_RATING_ACHIEVEMENT_ID_TO_AWARD_QUERY, new Object[]{userId, userId}, Integer.class);
    }

    public void updateAchievementUser(Integer achievementId, Integer userId) {
        log.debug("Updating achievement");
        jdbcTemplate.update(UPDATE_ACHIEVEMENT_USER_QUERY,
                achievementId, userId);
    }

    public Integer getUserAchievementsSum(Integer userId) {
        log.debug("Getting user achievements sum");
        List<Integer> integerList = jdbcTemplate.queryForList(GET_USER_ACHIEVEMENTS_SUM_QUERY, new Object[]{userId}, Integer.class);
        return integerList.size() == 0 ? null : integerList.get(0);
    }

    @Override
    public Achievement mapRow(ResultSet resultSet, int i) throws SQLException {
        log.debug("Mapping achievement rows");
        return new Achievement(
                resultSet.getInt("uid"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getString("icon"),
                resultSet.getInt("followers"),
                resultSet.getInt("posts"),
                resultSet.getFloat("rating"),
                resultSet.getInt("meetups")
        );
    }

}
