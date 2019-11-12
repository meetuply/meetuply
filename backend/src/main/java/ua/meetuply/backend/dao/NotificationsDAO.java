package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Notification;
import ua.meetuply.backend.model.SocketNotification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NotificationsDAO implements  RowMapper<Notification> {


    @Autowired
    public JdbcTemplate jdbcTemplate;


    private static String GET_NOTIFICATION_BY_ID_QUERY = "select t1.uid,t1.date_time,t1.is_read,t1.receiver_id , t2.html_text, t2.plain_text \n" +
            "from notification t1 \n" +
            "join notification_template t2 on t1.template_id = t2.uid \n" +
            "WHERE t1.uid = ?";
    private static String GET_NOTIFICATIONS_QUERY = "select t1.uid,t1.date_time,t1.is_read,t1.receiver_id , t2.html_text, t2.plain_text \n" +
            "from notification t1 \n" +
            "join notification_template t2 on t1.template_id = t2.uid";
    private static String SAVE_NOTIFICATION_QUERY = "INSERT INTO `notification` (`date_time`, `is_read`, `receiver_id`, `template_id`) " + "VALUES (?, ?, ?, ?)";
    private static String UPDATE_NOTIFICATION_QUERY = "UPDATE notification SET date_time = ?, is_read = ?, receiver_id = ?, template_id = ? WHERE uid = ?";
    private static String DELETE_NOTIFICATION_QUERY = "DELETE FROM notification WHERE uid = ?";

    private String GET_USER_NOTIFICATIONS_QUERY = "select t1.uid,t1.date_time,t1.is_read,t1.receiver_id , t2.html_text, t2.plain_text \n" +
            "from notification t1 \n" +
            "join notification_template t2 on t1.template_id = t2.uid \n" +
            "WHERE t1.receiver_id = ?";

    private String GET_USER_NOTIFICATIONS_BY_STATUS = "select t1.uid,t1.date_time,t1.is_read,t1.receiver_id , t2.html_text, t2.plain_text \n" +
            "from notification t1 \n" +
            "join notification_template t2 on t1.template_id = t2.uid \n" +
            "WHERE t1.receiver_id = ? \n" +
            "AND t1.is_read = ?";



    public SocketNotification get(Integer id) {
        return jdbcTemplate.queryForObject(GET_NOTIFICATION_BY_ID_QUERY, new Object[]{id}, new SocketNotificationRowMapper());
    }

    public List<SocketNotification> getAll() {
        return jdbcTemplate.query(GET_NOTIFICATIONS_QUERY, new SocketNotificationRowMapper());
    }


    public void save(Notification notification) {
        jdbcTemplate.update(SAVE_NOTIFICATION_QUERY,
                notification.getDateTime(), notification.getIsRead(), notification.getReceiverId(), notification.getTemplateId());
    }


    public void update(Notification notification) {
        jdbcTemplate.update(UPDATE_NOTIFICATION_QUERY,
                notification.getDateTime(), notification.getIsRead(), notification.getReceiverId(), notification.getTemplateId(), notification.getNotificationId());
    }


    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_NOTIFICATION_QUERY, id);
    }


    public List<SocketNotification> getUserNotifications(Integer userId) {
        return jdbcTemplate.query(GET_USER_NOTIFICATIONS_QUERY, new Object[]{userId}, new SocketNotificationRowMapper());
    }


    public List<SocketNotification> getReadedOrUnreadedNotifications(Integer userId, boolean readOrUnread) {
        return jdbcTemplate.query(GET_USER_NOTIFICATIONS_BY_STATUS, new Object[]{userId, readOrUnread}, new SocketNotificationRowMapper());
    }


    public Notification mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Notification(
                resultSet.getInt("uid"),
                resultSet.getTimestamp("date_time").toLocalDateTime(),
                resultSet.getBoolean("is_read"),
                resultSet.getInt("receiver_id"),
                resultSet.getInt("template_id")
        );
    }
}
