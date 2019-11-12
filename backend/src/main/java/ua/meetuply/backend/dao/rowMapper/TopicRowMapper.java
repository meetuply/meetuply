package ua.meetuply.backend.dao.rowMapper;

import ua.meetuply.backend.model.Topic;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicRowMapper implements RowMapper<Topic> {

    @Override
    public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
        Topic topic = new Topic();
        topic.setTopicId(rs.getInt("uid"));
        topic.setName(rs.getString("name"));

        return topic;
    }
}
