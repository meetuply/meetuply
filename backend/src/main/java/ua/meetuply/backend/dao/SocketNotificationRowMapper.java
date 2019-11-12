package ua.meetuply.backend.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.meetuply.backend.model.SocketNotification;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SocketNotificationRowMapper implements RowMapper<SocketNotification> {
    @Override
    public SocketNotification mapRow(ResultSet resultSet, int i) throws SQLException {
        return new SocketNotification(
                resultSet.getInt("uid"),
                resultSet.getTimestamp("date_time").toLocalDateTime(),
                resultSet.getBoolean("is_read"),
                resultSet.getInt("receiver_id"),
                resultSet.getString("html_text"),
                resultSet.getString("plain_text")
        );
    }
}
