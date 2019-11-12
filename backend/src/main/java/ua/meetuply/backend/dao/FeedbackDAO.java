package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.model.Feedback;
import ua.meetuply.backend.model.State;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.StateService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FeedbackDAO implements RowMapper<Feedback> {

    private static final String GET_ALL_QUERY = "SELECT * FROM feedback";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM feedback WHERE uid = ?";
    private static final String SAVE_QUERY = "INSERT INTO `feedback` (`uid`, `date_time`, `content`, `author_id`, `forwarded_to_id`) " + "VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE uid = ?";
    private static final String UPDATE_QUERY = "UPDATE feedback SET content = ?, date_time = ? WHERE uid = ?";

    private static final String GET_BY_FEEDBACK_TO = "SELECT * FROM feedback WHERE forwarded_to_id = ? order by date_time desc";
    private static final String GET_BY_AUTHOR = "SELECT * FROM feedback WHERE author_id = ? order by date_time desc";
    private static final String GET_BY_TO = "SELECT * FROM feedback WHERE author_id = ? AND forwarded_to_id = ? order by date_time desc";

    private static final String FIND_FEEDBACK_WAITING = "SELECT DISTINCT(speaker_id) FROM meetup AS m WHERE speaker_id<>? AND " +
            "(SELECT COUNT(f.uid) FROM feedback AS f WHERE f.forwarded_to_id = m.speaker_id AND f.author_id = ?) < " +
            "(SELECT COUNT(ma.meetup_id) FROM meetup_attendees AS ma WHERE ma.user_id = ? AND meetup_id IN (SELECT mm.uid FROM meetup AS mm WHERE mm.state_id = ? AND mm.speaker_id = m.speaker_id))";

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    AppUserService appUserService;

	@Autowired
    StateService stateService;
	
	public Feedback get(Integer feedbackId) {
        List<Feedback> feedbacks = jdbcTemplate.query(GET_BY_ID_QUERY, new Object[] {feedbackId}, this);
        return feedbacks.size() == 0 ? null : feedbacks.get(0);
    }

    public List<Feedback> getByFeedbackTo(Integer userId) {
        return jdbcTemplate.query(GET_BY_FEEDBACK_TO, new Object[] { userId }, this);
    }

    public List<Feedback> getByAuthor(Integer userId) {
        return jdbcTemplate.query(GET_BY_AUTHOR, new Object[] { userId }, this);
    }

    public Iterable<Feedback> getByTo(Integer idby, Integer idto) {
        return jdbcTemplate.query(GET_BY_TO, new Object[] { idby, idto }, this);
    }

    public List<Feedback> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, this);
    }

    public List<Integer> getFeedbacksWaiting(Integer attendee) throws NotFoundException {
        return jdbcTemplate.queryForList(FIND_FEEDBACK_WAITING,
                new Object[]{attendee, attendee, attendee, stateService.get(State.PASSED).getStateId()}, Integer.class);
    }
	
	public void save(Feedback feedback) {
        jdbcTemplate.update(SAVE_QUERY,
                feedback.getFeedbackId(),
                feedback.getDate(),
                feedback.getFeedbackContent(),
                feedback.getFeedbackBy(),
                feedback.getFeedbackTo());
    }

    public void update(Feedback feedback) {
        jdbcTemplate.update(UPDATE_QUERY,feedback.getFeedbackContent(), feedback.getDate(), feedback.getFeedbackId());
    }

    public void delete(Integer feedbackId) {
        jdbcTemplate.update(DELETE_QUERY, feedbackId);
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
