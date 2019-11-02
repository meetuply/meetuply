package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Repository
public class MeetupDAO implements IDAO<Meetup>, RowMapper<Meetup> {

    private static final String FIND_MEETUPS_BY_FILTER_DATES_RATING_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.finish_date_time <= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATEFROM_RATING_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATETO_RATING_QUERY = "SELECT * from meetup where meetup.finish_date_time <= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATES_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.finish_date_time <= ?";
    private static final String FIND_MEETUPS_BY_FILTER_DATEFROM_QUERY = "SELECT * from meetup where meetup.start_date_time >= ?";
    private static final String FIND_MEETUPS_BY_FILTER_DATETO_QUERY = "SELECT * from meetup where meetup.finish_date_time <= ?";
    private static final String FIND_MEETUPS_BY_FILTER_RATING_QUERY = "SELECT * from meetup where meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StateDAO stateDAO;

    @Override
    public Meetup get(Integer id) {
        List<Meetup> meetups = jdbcTemplate.query("SELECT * FROM meetup WHERE uid = ?", new Object[]{id}, this);
        return meetups.size() == 0 ? null : meetups.get(0);
    }

    @Override
    public List<Meetup> getAll() {
        List<Meetup> meetupList = jdbcTemplate.query("SELECT * FROM meetup", this);
        return meetupList;
    }

    @Override
    public void save(Meetup meetup) {


        jdbcTemplate.update("INSERT INTO meetup (`uid`,`place`, `title`, `description`,`registered_attendees`, `min_attendees`, `max_attendees`," +
                        "`start_date_time`, `finish_date_time`, `state_id`, `speaker_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", null,
                meetup.getMeetupPlace(), meetup.getMeetupTitle(), meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(Meetup meetup) {
        jdbcTemplate.update("UPDATE meetup SET place = ?, " +
                        "title = ?, description = ? ,registered_attendees = ?, min_attendees = ?," +
                        "max_attendees = ?, start_date_time = ?, finish_date_time = ?," +
                        "state_id = ?, speaker_id = ? WHERE uid = ?", meetup.getMeetupPlace(), meetup.getMeetupTitle(),
                meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId(),
                meetup.getMeetupId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM meetup WHERE uid = ?", id);
    }

    public List<Meetup> getMeetupsChunk(Integer startRow, Integer endRow) {
        List<Meetup> meetupList = jdbcTemplate.query("SELECT * FROM meetup order by start_date_time asc LIMIT ?, ?", new Object[]{startRow, endRow},
                this);
        return meetupList;
    }

    public Integer getUserMeetupsNumber(Integer userId) {
        Integer meetupsNumber = jdbcTemplate.queryForObject("select count(*) from meetup where speaker_id = ?;",
                new Object[]{userId}, Integer.class);
        return meetupsNumber != null ? meetupsNumber : 0;
    }

    @Override
    public Meetup mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meetup meetup = new Meetup();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        meetup.setMeetupId(rs.getInt("uid"));
        meetup.setMeetupPlace(rs.getString("place"));
        meetup.setMeetupTitle(rs.getString("title"));
        meetup.setMeetupDescription(rs.getString("description"));
        meetup.setMeetupRegisteredAttendees(rs.getInt("registered_attendees"));
        meetup.setMeetupMinAttendees(rs.getInt("min_attendees"));
        meetup.setMeetupMaxAttendees(rs.getInt("max_attendees"));
        meetup.setMeetupStartDateTime(LocalDateTime.parse(rs.getString("start_date_time"), formatter));
        meetup.setMeetupFinishDateTime(LocalDateTime.parse(rs.getString("finish_date_time"), formatter));
        meetup.setStateId(rs.getInt("state_id"));
        meetup.setSpeakerId(rs.getInt("speaker_id"));
        return meetup;
    }

    public void join(Integer meetupID, Integer userID) {
        jdbcTemplate.update("INSERT INTO `meetup_attendees` (`meetup_id`, `user_id`) VALUES (?, ?)",
                meetupID, userID);
        System.out.println("insert meetup " + meetupID + " user " + userID);
    }

    public void leave(Integer meetupID, Integer userID) {
        jdbcTemplate.update("DELETE FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?",
                meetupID, userID);

    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return jdbcTemplate.query("SELECT 1 FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?",
                new Object[]{meetupID, userID},
                new BeanPropertyRowMapper<>(Object.class)).size() > 0;

    }

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        Double rating = filter.getRating();
        Timestamp dateFrom = filter.getDateFrom();
        Timestamp dateTo = filter.getDateTo();
        if (rating != null) {
            return performQueryWhenRatingIsNotNull(rating, dateFrom, dateTo);
        } else {
            return performQueryWhenRatingIsNull(dateFrom, dateTo);
        }
    }

    public List<Meetup> find(SQLPredicate where) {
        StringBuilder query = new StringBuilder("SELECT * FROM meetup ");
        if (where != null) query.append("WHERE ").append(where.toString());
        System.out.println(query);
        return jdbcTemplate.query(query.toString(), this);
    }

    public List<Meetup> futureScheduledAndBookedMeetupsOf(AppUser user) {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", SQLPredicate.Operation.IN, Arrays.asList(stateDAO.get("Scheduled").getStateId(),
                        stateDAO.get("Booked").getStateId())),
                new SQLPredicate("speaker_id", SQLPredicate.Operation.EQUALS, user.getUserId())
        );
        SQLPredicate where = new SQLPredicate(SQLPredicate.Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> currentMeetupsOf(AppUser user) {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", SQLPredicate.Operation.EQUALS, stateDAO.get("In progress").getStateId()),
                new SQLPredicate("speaker_id", SQLPredicate.Operation.EQUALS, user.getUserId())
        );
        SQLPredicate where = new SQLPredicate(SQLPredicate.Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> notEnoughAttendees1Hour() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("start_date_time", SQLPredicate.Operation.LESS, "NOW() + INTERVAL 1 HOUR"),
                new SQLPredicate("state_id", SQLPredicate.Operation.EQUALS, stateDAO.get("Scheduled").getStateId()),
                new SQLPredicate("min_attendees", SQLPredicate.Operation.GREATER, "registered_attendees")
        );
        SQLPredicate where = new SQLPredicate(SQLPredicate.Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> goingToStart() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", SQLPredicate.Operation.IN, Arrays.asList(stateDAO.get("Scheduled").getStateId(),
                        stateDAO.get("Booked").getStateId())),
                new SQLPredicate("start_date_time", SQLPredicate.Operation.LESS, "NOW()")
        );
        SQLPredicate where = new SQLPredicate(SQLPredicate.Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> goingToFinish() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("finish_date_time", SQLPredicate.Operation.LESS, "NOW()"),
                new SQLPredicate("state_id", SQLPredicate.Operation.EQUALS, stateDAO.get("In progress").getStateId())
        );
        SQLPredicate where = new SQLPredicate(SQLPredicate.Operation.AND, andList);
        return find(where);
    }

    private List<Meetup> performQueryWhenRatingIsNull(Timestamp dateFrom, Timestamp dateTo) {
        if (nonNull(dateFrom) && isNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATEFROM_QUERY,
                    new Object[]{dateFrom}, this);
        } else if (isNull(dateFrom) && nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATETO_QUERY,
                    new Object[]{dateTo}, this);
        } else {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATES_QUERY,
                    new Object[]{dateFrom, dateTo}, this);
        }
    }

    private List<Meetup> performQueryWhenRatingIsNotNull(Double rating, Timestamp dateFrom, Timestamp dateTo) {
        if (nonNull(dateFrom) && nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATES_RATING_QUERY,
                    new Object[]{dateFrom, dateTo, rating}, this);
        } else if (nonNull(dateFrom)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATEFROM_RATING_QUERY,
                    new Object[]{dateFrom, rating}, this);
        } else if (nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATETO_RATING_QUERY,
                    new Object[]{dateTo, rating}, this);
        } else {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_RATING_QUERY,
                    new Object[]{rating}, this);
        }
    }
}