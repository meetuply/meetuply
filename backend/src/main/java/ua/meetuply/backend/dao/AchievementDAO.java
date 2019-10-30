package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Achievement;

import java.math.BigInteger;
import java.sql.*;
import java.util.List;

@Repository
public class AchievementDAO implements IDAO<Achievement>, RowMapper<Achievement> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public Achievement get(Integer id) {
        List<Achievement> achievements = jdbcTemplate.query("SELECT * FROM achievement WHERE uid = ?", new Object[] {id}, this);
        return achievements.size() == 0 ? null : achievements.get(0);
    }

    @Override
    public List<Achievement> getAll() {
        List<Achievement> achievements = jdbcTemplate.query("SELECT * FROM achievement", this);
        return achievements;
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        List<Achievement> achievements = jdbcTemplate.query("SELECT uid, title, description, icon," +
                "followers_number, posts_number, rating, meetups from user_achievement " +
                "inner join achievement on " +
                "user_achievement.achievement_id = achievement.uid " +
                "where user_id = ?", new Object[] {userId}, this);
        return achievements;
    }

    @Override
    public void save(Achievement achievement) {
        jdbcTemplate.update("INSERT INTO achievement (`title`, `description`, `icon`, `followers_number`, " +
                        "`posts_number`, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
        achievement.getFollowersNumber(), achievement.getPostsNumber(), achievement.getRating(),
                achievement.getMeetups());
    }

    public void saveAchievementMeetup(Integer achievementId, Integer topicId, Integer quantity){
        jdbcTemplate.update("Insert into achievement_topic (`achievement_id`, `topic_id`, `quantity`) " +
                "values (?,?,?)",
                achievementId, topicId, quantity);
    }

    public Integer saveReturnId(Achievement achievement) {
        String query = "INSERT INTO achievement (`title`, `description`, `icon`, `followers_number`, " +
                "`posts_number`, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, achievement.getTitle());
            ps.setString(2, achievement.getDescription());
            ps.setString(3, achievement.getIcon());
            ps.setObject(4, achievement.getFollowersNumber(), JDBCType.INTEGER);
            ps.setObject(5, achievement.getPostsNumber(), JDBCType.INTEGER);
            ps.setObject(6, achievement.getRating(), JDBCType.FLOAT);
            ps.setObject(7, achievement.getMeetups(), JDBCType.INTEGER);
            return ps;
        }, keyHolder);
        BigInteger b1 = (BigInteger) keyHolder.getKeys().get("GENERATED_KEY");
        return b1.intValue();
    }

    @Override
    public void update(Achievement achievement) {
        jdbcTemplate.update("UPDATE achievement SET title = ?, description = ?, " +
                "icon = ?, followers_number = ?, posts_number = ?, rating = ?, " +
                        "meetups = ? WHERE uid = ?",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowersNumber(), achievement.getPostsNumber(),
                achievement.getRating(), achievement.getMeetups(), achievement.getAchievementId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM achievement WHERE uid = ?", id);
    }

    @Override
    public Achievement mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Achievement(
                resultSet.getInt("uid"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getString("icon"),
                resultSet.getInt("followers_number"),
                resultSet.getInt("posts_number"),
                resultSet.getFloat("rating"),
                resultSet.getInt("meetups")
        );
    }
}
