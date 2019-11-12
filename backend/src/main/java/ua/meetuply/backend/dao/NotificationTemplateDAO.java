package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.NotificationTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificationTemplateDAO implements IDAO<NotificationTemplate>, RowMapper<NotificationTemplate> {


    private static String GET_NOTIFICATIONS_QUERY = "SELECT * FROM notification_template";

    private static String GET_NOTIFICATION_BY_ID_QUERY = "SELECT * FROM notification_template WHERE uid = ?";

    private static String GET_NOTIFICATION_BY_NAME_QUERY = "SELECT * FROM notification_template WHERE name = ?";

    private static String SAVE_NOTIFICATION_QUERY = "INSERT INTO `notification_template` (`name`, `html_text`, `plain_text`) " + "VALUES (?, ?, ?)";

    private static String UPDATE_NOTIFICATION_QUERY = "UPDATE notification_template SET name = ?, html_text = ?, plain_text = ? WHERE uid = ?";

    private static String DELETE_NOTIFICATION_QUERY = "DELETE FROM notification_template WHERE uid = ?";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public NotificationTemplate get(Integer id) {
        List<NotificationTemplate> notificationTemplates = jdbcTemplate.query(GET_NOTIFICATION_BY_ID_QUERY, new Object[] { id }, this);
        return notificationTemplates.size() == 0 ? null : notificationTemplates.get(0);
    }

    @Override
    public List<NotificationTemplate> getAll() {
        List<NotificationTemplate> notificationTemplates = jdbcTemplate.query(GET_NOTIFICATIONS_QUERY, this);
        return notificationTemplates;
    }

    @Override
    public void save(NotificationTemplate notificationTemplate) {
        jdbcTemplate.update(SAVE_NOTIFICATION_QUERY,
                notificationTemplate.getName(), notificationTemplate.getHtmlText(), notificationTemplate.getPlainText());
    }

    @Override
    public void update(NotificationTemplate notificationTemplate) {
        jdbcTemplate.update(UPDATE_NOTIFICATION_QUERY,
                notificationTemplate.getName(), notificationTemplate.getHtmlText(), notificationTemplate.getPlainText(),
                notificationTemplate.getNotificationTemplateId());
    }


    public NotificationTemplate getByName(String name) {
        List<NotificationTemplate> notificationTemplates = jdbcTemplate.query(GET_NOTIFICATION_BY_NAME_QUERY, new Object[] { name }, this);
        return notificationTemplates.size() == 0 ? null : notificationTemplates.get(0);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_NOTIFICATION_QUERY, id);
    }

    @Override
    public NotificationTemplate mapRow(ResultSet resultSet, int i) throws SQLException {
        return new NotificationTemplate(
                resultSet.getInt("uid"),
                resultSet.getString("name"),
                resultSet.getString("html_text"),
                resultSet.getString("plain_text")
        );
    }
}