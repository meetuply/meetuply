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

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public NotificationTemplate get(Integer id) {
        List<NotificationTemplate> notificationTemplates = jdbcTemplate.query("SELECT * FROM notification_template WHERE uid = ?", new Object[] { id }, this);
        return notificationTemplates.size() == 0 ? null : notificationTemplates.get(0);
    }

    @Override
    public List<NotificationTemplate> getAll() {
        List<NotificationTemplate> notificationTemplates = jdbcTemplate.query("SELECT * FROM notification_template", this);
        return notificationTemplates;
    }

    @Override
    public void save(NotificationTemplate notificationTemplate) {
        jdbcTemplate.update("INSERT INTO `notification_template` (`name`, `html_text`, `plain_text`) " + "VALUES (?, ?, ?)",
                notificationTemplate.getName(), notificationTemplate.getHtmlText(), notificationTemplate.getPlainText());
    }

    @Override
    public void update(NotificationTemplate notificationTemplate) {
        jdbcTemplate.update("UPDATE notification_template SET name = ?, html_text = ?, plain_text = ? WHERE uid = ?",
                notificationTemplate.getName(), notificationTemplate.getHtmlText(), notificationTemplate.getPlainText(),
                notificationTemplate.getNotificationTemplateId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM notification_template WHERE uid = ?", id);
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