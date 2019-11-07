package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Feedback;
import ua.meetuply.backend.service.AppUserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FeedbackDAO implements RowMapper<Feedback> {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    AppUserService appUserService;
	
	public Feedback get(Integer feedbackId) {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM feedback WHERE uid = ?", new Object[] {feedbackId}, this);
        return feedbacks.size() == 0 ? null : feedbacks.get(0);
    }

    public List<Feedback> getByFeedbackTo(Integer userId) {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM feedback WHERE forwarded_to_id = ? order by date_time desc", new Object[] { userId }, this);
        return feedbacks;
    }

    public List<Feedback> getByAuthor(Integer userId) {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM feedback WHERE author_id = ? order by date_time desc", new Object[] { userId }, this);
        return feedbacks;
    }

    public Iterable<Feedback> getByTo(Integer idby, Integer idto) {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM feedback WHERE author_id = ? AND forwarded_to_id = ? order by date_time desc", new Object[] { idby, idto }, this);
        return feedbacks;
    }

    public List<Feedback> getAll() {
        List<Feedback> feedbacks = jdbcTemplate.query("SELECT * FROM feedback", this);
        return feedbacks;
    }
	
	public void save(Feedback feedback) {
        jdbcTemplate.update("INSERT INTO `feedback` (`uid`, `date_time`, `content`, `author_id`, `forwarded_to_id`) " + "VALUES (?, ?, ?, ?, ?)",
                feedback.getFeedbackId(),
                feedback.getDate(),
                feedback.getFeedbackContent(),
                feedback.getFeedbackBy(),
                feedback.getFeedbackTo());
    }

    public void update(Feedback feedback) {
        jdbcTemplate.update("UPDATE feedback SET content = ? AND date_time = ? WHERE uid = ?",
        		feedback.getFeedbackContent(), feedback.getDate(), feedback.getFeedbackId());
    }

    public void delete(Integer feedbackId) {
        jdbcTemplate.update("DELETE FROM feedback WHERE uid = ?", feedbackId);
    }

    @Override
    public Feedback mapRow(ResultSet resultSet, int i) throws SQLException {
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(resultSet.getInt("uid"));
        feedback.setDate(resultSet.getTimestamp("date_time").toLocalDateTime());
        feedback.setFeedbackContent(resultSet.getString("content"));
        feedback.setFeedbackBy(resultSet.getInt("author_id"));
        feedback.setFeedbackTo(resultSet.getInt("forwarded_to_id"));
        return feedback;
    }


}
