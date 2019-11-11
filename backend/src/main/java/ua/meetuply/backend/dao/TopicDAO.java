package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Topic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class TopicDAO implements IDAO<Topic>, RowMapper<Topic> {

    private static final String GET_TOPICS_FOR_ACHIEVEMENT = "select * from topic where uid in (select topic_id from achievement_topic where achievement_id = ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Topic get(Integer id) {
        List<Topic> topics = jdbcTemplate.query("SELECT * FROM topic WHERE uid = ?", new Object[] { id }, this);
        return topics.size() == 0 ? null : topics.get(0);
    }

    public Integer getIdByName(String name) {
        List<Topic> topics = jdbcTemplate.query("SELECT * FROM topic WHERE name=? ", new Object[] { name }, this);
        return topics.size() == 0 ? null : topics.get(0).getTopicId();
    }

    public List<Topic> getByName(String term) {
        term = "%" + term + "%";
        return jdbcTemplate.query("SELECT * FROM topic WHERE name LIKE ?", new Object[] { term },this);
    }

    public Integer getTopicQuantity(Integer topicId) {
        return jdbcTemplate.queryForObject("SELECT quantity FROM achievement_topic WHERE topic_id=? limit 1;", new Object[] { topicId }, Integer.class);
    }

    public List<Topic> getAchievementTopics(Integer achievementId) {
        return jdbcTemplate.query(GET_TOPICS_FOR_ACHIEVEMENT, new Object[]{achievementId}, this);
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> topics = jdbcTemplate.query("SELECT * FROM topic", this);
        return topics;
    }

    @Override
    public void save(Topic topic) {
        jdbcTemplate.update("INSERT INTO `topic` (`name`) " + "VALUES (?)",
                topic.getName().toLowerCase());
    }

    @Override
    public void update(Topic topic) {
        jdbcTemplate.update("UPDATE topic SET name = ? WHERE uid = ?", topic.getName(), topic.getTopicId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM topic WHERE uid = ?", id);
    }

    @Override
    public Topic mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Topic(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }
}