package ua.meetuply.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ua.meetuply.backend.model.Topic;

@Repository
public class TopicDAO implements IDAO<Topic>, RowMapper<Topic> {
	
	@Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public Topic get(Integer id) {
        List<Topic> topics = jdbcTemplate.query("SELECT * FROM topic WHERE uid = ?", new Object[] { id }, this);
        return topics.size() == 0 ? null : topics.get(0);
    }

    @Override
    public List<Topic> getAll() {
        List<Topic> topics = jdbcTemplate.query("SELECT * FROM topic", this);
        return topics;
    }

    @Override
    public void save(Topic topic) {
        jdbcTemplate.update("INSERT INTO `topic` (`name`) " + "VALUES (?)",
                topic.getName());
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