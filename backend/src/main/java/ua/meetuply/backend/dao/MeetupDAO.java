package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.dao.SQLPredicate.Operation;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Meetup;
import ua.meetuply.backend.model.State.StateNames;
import ua.meetuply.backend.service.StateService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Repository
public class MeetupDAO implements IDAO<Meetup> {

    private static final String FIND_MEETUPS_BY_FILTER_DATES_RATING_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.finish_date_time <= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATEFROM_RATING_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATETO_RATING_QUERY = "SELECT * from meetup where meetup.finish_date_time <= ? AND meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String FIND_MEETUPS_BY_FILTER_DATES_QUERY = "SELECT * from meetup where meetup.start_date_time >= ? AND meetup.finish_date_time <= ?";
    private static final String FIND_MEETUPS_BY_FILTER_DATEFROM_QUERY = "SELECT * from meetup where meetup.start_date_time >= ?";
    private static final String FIND_MEETUPS_BY_FILTER_DATETO_QUERY = "SELECT * from meetup where meetup.finish_date_time <= ?";
    private static final String FIND_MEETUPS_BY_FILTER_RATING_QUERY = "SELECT * from meetup where meetup.speaker_id in (select rated_user_id from rating group by rated_user_id having avg(value) >= ?)";
    private static final String GET_MEETUP_CHUNK_WITH_USERNAME_AND_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on meetup.speaker_id = u.uid\n" +
            "inner join (select uid, coalesce((select avg(value) from rating where rated_user_id = uid), 0.0) as rating\n" +
            "from user) as r on meetup.speaker_id = r.uid\n" +
            "order by start_date_time desc limit ?, ?;";
    private static final String IS_ATTENDEE_QUERY = "SELECT 1 FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?";
    private static final String LEAVE_MEETUP_QUERY = "DELETE FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?";
    private static final String JOIN_MEETUP_QUERY = "INSERT INTO `meetup_attendees` (`meetup_id`, `user_id`) VALUES (?, ?)";
    private static final String GET_USER_MEETUPS_NUMBER_QUERY = "select count(*) from meetup where speaker_id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM meetup";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM meetup WHERE uid = ?";
    private static final String SAVE_QUERY = "INSERT INTO meetup (`uid`,`place`, `title`, `description`,`registered_attendees`, `min_attendees`, `max_attendees`," +
            "`start_date_time`, `finish_date_time`, `state_id`, `speaker_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM meetup WHERE uid = ?";
    private static final String UPDATE_QUERY = "UPDATE meetup SET place = ?, " +
            "title = ?, description = ? ,registered_attendees = ?, min_attendees = ?," +
            "max_attendees = ?, start_date_time = ?, finish_date_time = ?," +
            "state_id = ?, speaker_id = ? WHERE uid = ?";
    private static final String GET_USER_FUTURE_MEETUPS = "SELECT *\n" +
            "FROM meetup\n" +
            "WHERE speaker_id = ? AND state_id IN (SELECT uid FROM state WHERE LOWER(name) in (LOWER('scheduled'),lower('booked')))" +
            "order by start_date_time asc;";
    private static final String GET_USER_PAST_MEETUPS = "SELECT * FROM meetup WHERE speaker_id = ? AND finish_date_time < now() order by start_date_time desc";
    private static final String GET_ACTIVE_MEETUPS_CHUNK_WITH_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on meetup.speaker_id = u.uid\n" +
            "inner join (select uid, coalesce((select avg(value) from rating where rated_user_id = uid), 0.0) as rating\n" +
            "from user where is_deactivated = 0) as r\n" +
            "on r.uid = speaker_id\n" +
            "where state_id IN (SELECT uid FROM state WHERE LOWER(name) in ('scheduled','booked'))\n" +
            "order by start_date_time desc limit ?, ?;";
    private static final String GET_USER_MEETUPS_CHUNK_WITH_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on\n" +
            "meetup.speaker_id = u.uid\n" +
            "inner join (select coalesce(rated_user_id, ?) as rated_user_id, coalesce(avg(value), 0.0) as rating from rating\n" +
            "where rated_user_id = ?) as r on u.uid = r.rated_user_id\n" +
            "and speaker_id = ?\n" +
            "order by start_date_time desc limit ?, ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StateService stateService;

    @Override
    public Meetup get(Integer id) {
        List<Meetup> meetups = jdbcTemplate.query(GET_BY_ID_QUERY, new Object[]{id}, new MeetupRowMapper());
        return meetups.size() == 0 ? null : meetups.get(0);
    }

    @Override
    public List<Meetup> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, new MeetupRowMapper());
    }

    @Override
    public void save(Meetup meetup) {
        jdbcTemplate.update(SAVE_QUERY, null,
                meetup.getMeetupPlace(), meetup.getMeetupTitle(), meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(Meetup meetup) {
        jdbcTemplate.update(UPDATE_QUERY, meetup.getMeetupPlace(), meetup.getMeetupTitle(),
                meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId(),
                meetup.getMeetupId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<Meetup> getMeetupsChunkWithUsernameAndRating(Integer startRow, Integer endRow) {
        List<Meetup> meetupList = jdbcTemplate.query(GET_MEETUP_CHUNK_WITH_USERNAME_AND_RATING, new Object[]{startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
        return meetupList;
    }

    public List<Meetup> getMeetupsChunkActive(Integer startRow, Integer endRow) {
        List<Meetup> meetupList = jdbcTemplate.query(GET_ACTIVE_MEETUPS_CHUNK_WITH_RATING, new Object[]{startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
        return meetupList;
    }

    public List<Meetup> getUserMeetupsChunk(Integer userId, Integer startRow, Integer endRow) {
        List<Meetup> meetupList = jdbcTemplate.query(GET_USER_MEETUPS_CHUNK_WITH_RATING, new Object[]{userId, userId,userId,startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
        return meetupList;
    }

    public List<Meetup> getUserFutureMeetups(Integer userId){
        return jdbcTemplate.query(GET_USER_FUTURE_MEETUPS, new Object[]{userId},
                new MeetupRowMapper());
    }

    public List<Meetup> getUserPastMeetups(Integer userId){
        return jdbcTemplate.query(GET_USER_PAST_MEETUPS, new Object[]{userId},
                new MeetupRowMapper());
    }

    public Integer getUserMeetupsNumber(Integer userId) {
        Integer meetupsNumber = jdbcTemplate.queryForObject(GET_USER_MEETUPS_NUMBER_QUERY,
                new Object[]{userId}, Integer.class);
        return meetupsNumber != null ? meetupsNumber : 0;
    }

    public void join(Integer meetupID, Integer userID) {
        jdbcTemplate.update(JOIN_MEETUP_QUERY,
                meetupID, userID);
        System.out.println("insert meetup " + meetupID + " user " + userID);
    }

    public void leave(Integer meetupID, Integer userID) {
        jdbcTemplate.update(LEAVE_MEETUP_QUERY,
                meetupID, userID);

    }

    public boolean isAttendee(Integer meetupID, Integer userID) {
        return jdbcTemplate.query(IS_ATTENDEE_QUERY,
                new Object[]{meetupID, userID},
                new BeanPropertyRowMapper<>(Object.class)).size() > 0;
    }

    public List<Meetup> findMeetupsByFilter(Filter filter) {
        Double rating = filter.getRatingFrom();
        Timestamp dateFrom = filter.getDateFrom();
        Timestamp dateTo = filter.getDateTo();
        if (rating != null) {
            return performQueryWhenRatingIsNotNull(rating, dateFrom, dateTo);
        } else {
            return performQueryWhenRatingIsNull(dateFrom, dateTo);
        }
    }

    public List<Meetup> find(SQLPredicate where) {
        StringBuilder query = new StringBuilder(GET_ALL_QUERY+" ");
        if (where != null) query.append("WHERE ").append(where.toString());
        System.out.println(query);
        return jdbcTemplate.query(query.toString(), new MeetupRowMapper());
    }

    public List<Meetup> futureScheduledAndBookedMeetupsOf(AppUser user) {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", Operation.IN,
                        Arrays.asList(stateService.get(StateNames.SCHEDULED.name).getStateId(),
                                      stateService.get(StateNames.BOOKED.name).getStateId())),
                new SQLPredicate("speaker_id", Operation.EQUALS, user.getUserId())
        );
        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> currentMeetupsOf(AppUser user) {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", Operation.EQUALS,
                        stateService.get(StateNames.IN_PROGRESS.name).getStateId()),
                new SQLPredicate("speaker_id", Operation.EQUALS, user.getUserId())
        );
        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> notEnoughAttendees1Hour() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("start_date_time", Operation.LESS, "NOW() + INTERVAL 1 HOUR"),
                new SQLPredicate("state_id", Operation.EQUALS,
                        stateService.get(StateNames.SCHEDULED.name).getStateId()),
                new SQLPredicate("min_attendees", Operation.GREATER, "registered_attendees")
        );
        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> goingToStart() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("state_id", Operation.IN,
                        Arrays.asList(stateService.get(StateNames.SCHEDULED.name).getStateId(),
                                      stateService.get(StateNames.BOOKED.name).getStateId())),
                new SQLPredicate("start_date_time", Operation.LESS, "NOW()")
        );
        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }

    public List<Meetup> goingToFinish() {
        List<SQLPredicate> andList = Arrays.asList(
                new SQLPredicate("finish_date_time", Operation.LESS, "NOW()"),
                new SQLPredicate("state_id", Operation.EQUALS,
                        stateService.get(StateNames.IN_PROGRESS.name).getStateId())
        );
        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }

    private List<Meetup> performQueryWhenRatingIsNull(Timestamp dateFrom, Timestamp dateTo) {
        if (nonNull(dateFrom) && isNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATEFROM_QUERY,
                    new Object[]{dateFrom}, new MeetupRowMapper());
        } else if (isNull(dateFrom) && nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATETO_QUERY,
                    new Object[]{dateTo}, new MeetupRowMapper());
        } else {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATES_QUERY,
                    new Object[]{dateFrom, dateTo}, new MeetupRowMapper());
        }
    }

    private List<Meetup> performQueryWhenRatingIsNotNull(Double rating, Timestamp dateFrom, Timestamp dateTo) {
        if (nonNull(dateFrom) && nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATES_RATING_QUERY,
                    new Object[]{dateFrom, dateTo, rating}, new MeetupRowMapper());
        } else if (nonNull(dateFrom)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATEFROM_RATING_QUERY,
                    new Object[]{dateFrom, rating}, new MeetupRowMapper());
        } else if (nonNull(dateTo)) {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_DATETO_RATING_QUERY,
                    new Object[]{dateTo, rating}, new MeetupRowMapper());
        } else {
            return jdbcTemplate.query(FIND_MEETUPS_BY_FILTER_RATING_QUERY,
                    new Object[]{rating}, new MeetupRowMapper());
        }
    }

    public List<Meetup> findBy(Filter filter) {
        List<SQLPredicate> andList = new LinkedList<>();

        if (filter.getDateFrom() != null)
            andList.add(new SQLPredicate("start_date_time", Operation.GREATER_EQUALS, filter.getDateFrom()));
        if (filter.getDateTo() != null)
            andList.add(new SQLPredicate("finish_date_time", Operation.LESS_EQUALS, filter.getDateTo()));

        if (filter.getRatingFrom() != null)
            andList.add(new SQLPredicate("speaker_id", Operation.IN,
                        new SQLSelect("avg_rating", "user_id",
                                new SQLPredicate("value", Operation.GREATER_EQUALS, filter.getRatingFrom()))));
        if (filter.getRatingTo() != null)
            andList.add(new SQLPredicate("speaker_id", Operation.IN,
                        new SQLSelect("avg_rating", "user_id",
                                new SQLPredicate("value", Operation.LESS_EQUALS, filter.getRatingTo()))));

        System.out.println(new SQLPredicate(Operation.AND, Arrays.asList(
                new SQLPredicate("meetup_id", Operation.EQUALS, "uid"),
                new SQLPredicate("topic_id", Operation.IN, filter.getTopicIds()))));

        if (filter.getTopicIds() != null && !filter.getTopicIds().isEmpty())
            andList.add(new SQLPredicate(Operation.EXISTS,
                                new SQLSelect("meetup_topic", "topic_id",
                                        new SQLPredicate(Operation.AND, Arrays.asList(
                                                new SQLPredicate("meetup_id", Operation.EQUALS, "uid"),
                                                new SQLPredicate("topic_id", Operation.IN, filter.getTopicIds())
                                                )))));

        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return find(where);
    }
    // EXAMPLE SELECT * FROM meetup
    // WHERE start_date_time >= '2000-11-09T14:49:21'
    //   AND finish_date_time <= '2010-11-12T14:49:28'
    //   AND speaker_id IN (SELECT user_id FROM avg_rating
    //                      WHERE value >= 3.0)
    //   AND speaker_id IN (SELECT user_id FROM avg_rating
    //                      WHERE value <= 5.0)
    //   AND  EXISTS (SELECT topic_id FROM meetup_topic
    //                WHERE meetup_id = uid
    //                  AND topic_id IN (9, 10) )
}