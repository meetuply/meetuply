package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Achievement;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public void save(Achievement achievement) {
        jdbcTemplate.update("INSERT INTO achievement (`title`, `description`, `icon`, `followers_number`, " +
                        "`posts_number`, `rating`) VALUES (?, ?, ?, ?, ?, ?)",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
        achievement.getFollowersNumber(), achievement.getPostsNumber(), achievement.getRating());
    }

    @Override
    public void update(Achievement achievement) {
        jdbcTemplate.update("UPDATE achievement SET title = ?, description = ?, " +
                "icon = ?, followers_number = ?, posts_number = ?, rating = ? WHERE uid = ?",
                achievement.getTitle(), achievement.getDescription(), achievement.getIcon(),
                achievement.getFollowersNumber(), achievement.getPostsNumber(),
                achievement.getRating(), achievement.getAchievementId());
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
                resultSet.getFloat("rating")
        );
    }
}
