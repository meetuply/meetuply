package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.NotificationTemplateService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class NotificationsDAO implements IDAO<Notification>, RowMapper<Notification>{


    @Autowired
    public JdbcTemplate jdbcTemplate;
    AppUserService appUserService;
    NotificationTemplateService notificationTemplateService;

    private String SqlGetAllUserNotifications = "SELECT * FROM notification n, notification_template nt " +
            "WHERE n.receiver_id = ? AND n.template_id = nt.uid";

    private String SqlGetCurrentUserNotification = "SELECT * FROM notification n, notification_template nt " +
            "WHERE n.receiver_id = ? AND nt.uid = ? AND n.template_id = nt.uid";

    private String SqlGetReadedOrUnreadedNotifications = "Select * From notification n, notification_template nt " +
            "WHERE n.receiver_id = ? " +
            "AND n.template_id = nt.uid " +
            "AND n.is_read = ?";


    //user's id
    @Override
    public Notification get(Integer id) {
        List<Notification> notification = jdbcTemplate.query("SELECT * FROM notification WHERE uid = ?", new Object[]{id}, this);
        return notification.size() == 0 ? null : notification.get(0);
    }

    @Override
    public List<Notification> getAll() {
        List<Notification> notification = jdbcTemplate.query("SELECT * FROM notification", this);
        return notification.size() == 0 ? null : notification;
    }

    @Override
    public void save(Notification notification) {
        jdbcTemplate.update("INSERT INTO `notification` (`date_time`, `is_read`, `receiver_id`, `template_id`) " + "VALUES (?, ?, ?, ?)",
                notification.getDate_time(), notification.getIsRead(), notification.getReceiverID(), notification.getTemplateID());
    }

    @Override
    public void update(Notification notification) {
        jdbcTemplate.update("UPDATE notification SET date_time = ?, is_read = ?, receiver_id = ?, template_id = ? WHERE uid = ?",
                notification.getDate_time(), notification.getIsRead(), notification.getReceiverID(), notification.getTemplateID(), notification.getNotificationID());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM notification WHERE uid = ?", id);
    }

    @Override
    public Notification mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Notification(
                resultSet.getInt("uid"),
                resultSet.getTimestamp("date_time").toLocalDateTime(),
                resultSet.getInt("is_read"),
                resultSet.getInt("receiver_id"),
                resultSet.getInt("template_id")
        );
    }

    public List<Map<String, Object>> getAllUserNotifications(Integer userId) {
        return jdbcTemplate.queryForList(SqlGetAllUserNotifications, userId);
    }

    public List<Map<String, Object>> getCurrentUserNotification(Integer userId, Integer notificationId) {
        return jdbcTemplate.queryForList(SqlGetCurrentUserNotification, userId, notificationId);
    }

    public List<Map<String, Object>> getReadedOrUnreadedNotifications(Integer userId, Integer readOrUnread) {
        return jdbcTemplate.queryForList(SqlGetReadedOrUnreadedNotifications, userId, readOrUnread);
    }

}
