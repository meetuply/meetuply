package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Achievement;

import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AchievementDAO implements IDAO<Achievement>, RowMapper<Achievement> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

//    public List<Map<String, Object>> test(Integer userId){
//         return getAchievementIdTopicQuantityForUserSum(userId);
//    }

    @Override
    public Achievement get(Integer id) {
        List<Achievement> achievements = jdbcTemplate.query("SELECT * FROM achievement WHERE uid = ?", new Object[]{id}, this);
        return achievements.size() == 0 ? null : achievements.get(0);
    }

    @Override
    public List<Achievement> getAll() {
        List<Achievement> achievements = jdbcTemplate.query("SELECT * FROM achievement", this);
        return achievements;
    }

    public List<Achievement> getUserAchievements(Integer userId) {
        List<Achievement> achievements = jdbcTemplate.query("SELECT uid, title, description, icon," +
                "followers, posts, rating, meetups from user_achievement " +
                "inner join achievement on " +
                "user_achievement.achievement_id = achievement.uid " +
                "where user_id = ?", new Object[]{userId}, this);
        return achievements;
    }

    @Override
    public void save(Achievement achievement) {
        jdbcTemplate.update("INSERT INTO achievement (`title`, `description`, `icon`, followers, " +
                        "posts, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowers(), achievement.getPosts(), achievement.getRating(),
                achievement.getMeetups());
    }

    public void saveAchievementMeetup(Integer achievementId, Integer topicId, Integer quantity) {
        jdbcTemplate.update("Insert into achievement_topic (`achievement_id`, `topic_id`, `quantity`) " +
                        "values (?,?,?)",
                achievementId, topicId, quantity);
    }

    public Integer saveReturnId(Achievement achievement) {
        String query = "INSERT INTO achievement (`title`, `description`, `icon`, followers, " +
                "posts, `rating`, `meetups`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, achievement.getTitle());
            ps.setString(2, achievement.getDescription());
            ps.setString(3, achievement.getIcon());
            ps.setObject(4, achievement.getFollowers(), JDBCType.INTEGER);
            ps.setObject(5, achievement.getPosts(), JDBCType.INTEGER);
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
                        "icon = ?, followers = ?, posts = ?, rating = ?, " +
                        "meetups = ? WHERE uid = ?",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowers(), achievement.getPosts(),
                achievement.getRating(), achievement.getMeetups(), achievement.getAchievementId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM achievement WHERE uid = ?", id);
    }

    //achievements section
//    public List<Map<String, Object>> getAchievementIdTopicQuantityForUserSum(Integer userId) {
//        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("select achievement_id, topic_id, quantity from achievement_topic\n" +
//                        "where achievement_id in (select achievement_id from achievement_topic\n" +
//                        "group by achievement_id\n" +
//                        "having sum(quantity) = (select count(*) from meetup where speaker_id = ?));",
//                new Object[]{userId});
//        Map<String, String> result = new HashMap<>();
//    }

    public List<Map<String, Object>> getUserMeetupTopic(Integer userId){
        return jdbcTemplate.queryForList("select topic_id, count(topic_id) as topic_number from meetup_topic as mt\n" +
                "inner join meetup as m on mt.meetup_id = m.uid\n" +
                "where speaker_id = ?\n" +
                "group by (topic_id);", new Object[]{userId});
    }

    public Integer getMeetupAchievementId(Integer userId){
        return jdbcTemplate.queryForObject("select uid from achievement\n" +
                "where uid not in\n" +
                "(select achievement_id from user_achievement where user_id = ?)\n" +
                "and meetups in (select count(*) from meetup where speaker_id = ?)\n" +
                "and posts is null\n" +
                "and followers is null\n" +
                "and rating is null\n" +
                "order by followers asc\n" +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public List<Integer> getNextMeetupNumberAchievementId(Integer userId) {
        return jdbcTemplate.queryForList("select meetups, uid from achievement" +
                " where uid not in" +
                "  (select achievement_id from user_achievement where user_id = ?)" +
                " and uid not in " +
                "    (select achievement_id from achievement_topic)" +
                "    and meetups > 0" +
                "    and followers is null" +
                "    and posts is null" +
                "    order by meetups asc" +
                "    limit 1", new Object[]{userId}, Integer.class);
    }

    public Integer getFollowersAchievementId(Integer userId){
        return jdbcTemplate.queryForObject("select uid from achievement\n" +
                "where uid not in\n" +
                "(select achievement_id from user_achievement where user_id = ?)\n" +
                "and followers in (select count(*) from followers where followed_user_id = ?)\n" +
                "and meetups is null\n" +
                "and posts is null\n" +
                "and rating is null\n" +
                "order by followers asc\n" +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public List<Integer> getNextFollowersNumberAchievementId(Integer userId){
        return jdbcTemplate.queryForList("select followers, uid from achievement " +
                "where uid not in " +
                "(select achievement_id from user_achievement where user_id = ?) " +
                "and followers > 0 " +
                "and meetups is null " +
                "and posts is null " +
                "and rating is null\n" +
                "order by followers asc " +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public Integer getPostsAchievementId(Integer userId){
        return jdbcTemplate.queryForObject("select uid from achievement " +
                "where uid not in " +
                "(select achievement_id from user_achievement where user_id = ?)\n" +
                "and posts in (select count(*) from post where author_id = ?)\n" +
                "and meetups is null\n" +
                "and followers is null\n" +
                "and rating is null\n" +
                "order by posts asc\n" +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public List<Integer> getNextPostsNumberAchievementId(Integer userId){
        return jdbcTemplate.queryForList("select posts, uid from achievement " +
                "where uid not in " +
                "(select achievement_id from user_achievement where user_id = ?) " +
                "and posts > 0 " +
                "and meetups is null " +
                "and followers is null " +
                "and rating is null\n" +
                "order by posts asc " +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public Integer getRatingAchievementId(Integer userId){
        return jdbcTemplate.queryForObject("select uid from achievement\n" +
                "where uid not in\n" +
                "(select achievement_id from user_achievement where user_id = ?)\n" +
                "and rating in (select avg(value) from rating where rated_user_id = ?)\n" +
                "and posts is null\n" +
                "and followers is null\n" +
                "and meetups is null\n" +
                "order by rating asc\n" +
                "limit 1;", new Object[]{userId}, Integer.class);
    }

    public void updateAchievementUser(Integer achievementId, Integer userId){
        jdbcTemplate.update("insert into user_achievement (`achievement_id`, `user_id`) values (?, ?)",
                achievementId, userId);
    }

    @Override
    public Achievement mapRow(ResultSet resultSet, int i) throws SQLException {
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
