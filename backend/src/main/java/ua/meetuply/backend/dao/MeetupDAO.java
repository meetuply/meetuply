package ua.meetuply.backend.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.controller.exception.NotFoundException;
import ua.meetuply.backend.dao.SQLPredicate.Operation;
import ua.meetuply.backend.dao.rowMapper.MeetupJoinedWithUserRowMapper;
import ua.meetuply.backend.dao.rowMapper.MeetupRowMapper;
import ua.meetuply.backend.dao.rowMapper.TopicRowMapper;
import ua.meetuply.backend.model.*;
import ua.meetuply.backend.service.LanguageService;
import ua.meetuply.backend.service.StateService;
import ua.meetuply.backend.service.TopicService;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Slf4j
@Repository
public class MeetupDAO implements IDAO<Meetup> {

    private static final String GET_MEETUP_CHUNK_WITH_USERNAME_AND_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on meetup.speaker_id = u.uid\n" +
            "inner join (select uid, coalesce((select avg(value) from rating where rated_user_id = uid), 0.0) as rating\n" +
            "from user where is_deactivated = 0) as r on meetup.speaker_id = r.uid\n" +
            "order by start_date_time desc limit ?, ?;";
    private static final String IS_ATTENDEE_QUERY = "SELECT 1 FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?";
    private static final String LEAVE_MEETUP_QUERY = "DELETE FROM `meetup_attendees` WHERE `meetup_id` = ? AND `user_id` = ?";
    private static final String JOIN_MEETUP_QUERY = "INSERT INTO `meetup_attendees` (`meetup_id`, `user_id`) VALUES (?, ?)";
    private static final String GET_USER_MEETUPS_NUMBER_QUERY = "select count(*) from meetup where speaker_id = ?";
    private static final String GET_ALL_QUERY = "SELECT * FROM meetup";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM meetup WHERE uid = ?";
    private static final String SAVE_QUERY = "INSERT INTO `meetup` (`place`, `title`, `description`,`registered_attendees`, `min_attendees`, `max_attendees`," +
            "`start_date_time`, `finish_date_time`, `state_id`, `speaker_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM meetup WHERE uid = ?";
    private static final String UPDATE_QUERY = "UPDATE meetup SET place = ?, " +
            "title = ?, description = ? ,registered_attendees = ?, min_attendees = ?," +
            "max_attendees = ?, start_date_time = ?, finish_date_time = ?," +
            "state_id = ?, speaker_id = ? WHERE uid = ?";
    private static final String GET_USER_FUTURE_MEETUPS = "SELECT *\n" +
            "FROM meetup\n" +
            "WHERE speaker_id = ? AND state_id IN (SELECT uid FROM state WHERE LOWER(name) in ('scheduled','booked')) and " +
            "start_date_time > now() " +
            "order by start_date_time asc;";
    private static final String GET_USER_PAST_MEETUPS = "SELECT * FROM meetup WHERE speaker_id = ? AND finish_date_time < now() order by start_date_time desc";
    private static final String GET_ACTIVE_MEETUPS_CHUNK_WITH_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on meetup.speaker_id = u.uid\n" +
            "inner join (select uid, coalesce((select avg(value) from rating where rated_user_id = uid), 0.0) as rating\n" +
            "from user) as r\n" +
            "on r.uid = speaker_id\n" +
            "where start_date_time > now() and state_id in (select uid from state where lower(name) in ('scheduled', 'booked'))\n" +
            "order by start_date_time asc limit ?, ?;";
    private static final String GET_USER_MEETUPS_CHUNK_WITH_RATING = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on\n" +
            "meetup.speaker_id = u.uid\n" +
            "inner join (select coalesce(rated_user_id, ?) as rated_user_id, coalesce(avg(value), 0.0) as rating from rating\n" +
            "where rated_user_id = ?) as r on u.uid = r.rated_user_id\n" +
            "and speaker_id = ?\n" +
            "order by start_date_time desc limit ?, ?;";
    private static final String GET_USER_MEETUPS_BEFORE_DAY = "SELECT * from meetup\n" +
            "inner join (select uid, firstname, surname, photo from user) as u on meetup.speaker_id = u.uid\n" +
            "inner join (select uid, coalesce((select avg(value) from rating where rated_user_id = uid), 0.0) as rating\n" +
            "from user where is_deactivated = 0) as r on meetup.speaker_id = r.uid\n" +
            "where meetup.uid in (select meetup_id from meetup_attendees where user_id = ?) and\n" +
            "start_date_time > now() and start_date_time < (now() + interval ? day)\n" +
            "order by start_date_time asc;";



    private static final String GET_TOPICS_BY_MEETUP = "SELECT * from `topic` WHERE `uid` in (SELECT `topic_id` from `meetup_topic` WHERE `meetup_id` = ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StateService stateService;

    @Autowired
    private LanguageService languageService;

    @Resource
    TopicService topicService;

    @Override
    public Meetup get(Integer id) {
        log.debug("Geeting meetup by id {}", id);
        List<Meetup> meetups = jdbcTemplate.query(GET_BY_ID_QUERY, new Object[]{id}, new MeetupRowMapper());
        return meetups.size() == 0 ? null : meetups.get(0);
    }

    @Override
    public List<Meetup> getAll() {
        log.debug("Geeting all meetups");
        return jdbcTemplate.query(GET_ALL_QUERY, new MeetupRowMapper());
    }

    @Override
    public void save(Meetup meetup) {
        log.debug("Saving meetup");
        jdbcTemplate.update(SAVE_QUERY,
                meetup.getMeetupPlace(), meetup.getMeetupTitle(), meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId());
    }


    public void saveFull(FullMeetup meetup) {
        log.debug("Saving fullMeetup");
        Connection con;
        try {
            con = jdbcTemplate.getDataSource().getConnection();
            con.setAutoCommit(false);
            Statement statement = con.createStatement();
            String str1 = "INSERT INTO `meetup` (`place`, `title`, `description`,`registered_attendees`, `min_attendees`, `max_attendees`," +
                    "`start_date_time`, `finish_date_time`, `state_id`, `speaker_id`) VALUES (" +
                    "'" + meetup.getMeetupPlace() + "'," +
                    "'" + meetup.getMeetupTitle() + "'," +
                    "'" + meetup.getMeetupDescription() + "'," +
                    meetup.getMeetupRegisteredAttendees() + "," +
                    meetup.getMeetupMinAttendees() + "," +
                    meetup.getMeetupMaxAttendees() + ",'" +
                    meetup.getMeetupStartDateTime() + "','" +
                    meetup.getMeetupFinishDateTime() + "'," +
                    meetup.getStateId() + "," +
                    meetup.getSpeakerId() + ")";
            statement.executeUpdate(str1, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            con.commit();
            rs.next();
            Integer id = rs.getInt(1);
            String str2 = "INSERT INTO `meetup_language` (`language_id`,`meetup_id`) VALUES (" +
                    languageService.get(meetup.getLanguage()).getLanguageId() + "," +
                    id + ")";
            statement.executeUpdate(str2);
            for (Integer topic : meetup.getTopics()) {
                String query = "insert into `meetup_topic` (topic_id,meetup_id) values('"
                        + topic + "','" + id + "')";
                statement.addBatch(query);
            }
            statement.executeBatch();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Topic> getMeetupTopics(Integer i) {
        return jdbcTemplate.query(GET_TOPICS_BY_MEETUP, new Object[]{i},
                new TopicRowMapper());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void update(Meetup meetup) {
        log.debug("Updating meetup");
        jdbcTemplate.update(UPDATE_QUERY, meetup.getMeetupPlace(), meetup.getMeetupTitle(),
                meetup.getMeetupDescription(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId(),
                meetup.getMeetupId());
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting meetup");
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public List<Meetup> getUserMeetupsBeforeDay(Integer userId, int day) {
        log.debug("Getting user meetups for closest {} days", day);
        return jdbcTemplate.query(GET_USER_MEETUPS_BEFORE_DAY,  new Object[]{userId, day}, new MeetupJoinedWithUserRowMapper());
    }

    public List<Meetup> getMeetupsChunkWithUsernameAndRating(Integer startRow, Integer endRow) {
        log.debug("Getting meetups chunk with username and rating");
        return jdbcTemplate.query(GET_MEETUP_CHUNK_WITH_USERNAME_AND_RATING, new Object[]{startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
    }

    public List<Meetup> getMeetupsChunkActive(Integer startRow, Integer endRow) {
        log.debug("Getting active speaker meetups");
        return jdbcTemplate.query(GET_ACTIVE_MEETUPS_CHUNK_WITH_RATING, new Object[]{startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
    }

    public List<Meetup> getUserMeetupsChunk(Integer userId, Integer startRow, Integer endRow) {
        log.debug("Getting user meetups chunk with rating");
        return jdbcTemplate.query(GET_USER_MEETUPS_CHUNK_WITH_RATING, new Object[]{userId, userId,userId,startRow, endRow},
                new MeetupJoinedWithUserRowMapper());
    }

    public List<Meetup> getUserFutureMeetups(Integer userId) {
        log.debug("Getting user future meetups");
        return jdbcTemplate.query(GET_USER_FUTURE_MEETUPS, new Object[]{userId},
                new MeetupRowMapper());
    }

    public List<Meetup> getUserPastMeetups(Integer userId) {
        log.debug("Getting user past meetups");
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

    public List<Meetup> find(SQLPredicate where) {
        StringBuilder query = new StringBuilder(GET_ALL_QUERY+" ");
        if (where != null) query.append("WHERE ").append(where.toString());
        return jdbcTemplate.query(query.toString(), new MeetupRowMapper());
    }

    public List<Meetup> futureScheduledAndBookedMeetupsOf(AppUser user) throws NotFoundException {
        return find(
                new SQLPredicate(Operation.AND,
                            new SQLPredicate("state_id", Operation.IN,
                                    stateService.get(State.SCHEDULED).getStateId(),
                                    stateService.get(State.BOOKED).getStateId()),
                            new SQLPredicate("speaker_id", Operation.EQUALS,
                                    user.getUserId())));
    }

    public List<Meetup> currentMeetupsOf(AppUser user) throws NotFoundException {
        return find(
                new SQLPredicate(Operation.AND,
                        new SQLPredicate("state_id", Operation.EQUALS,
                                    stateService.get(State.IN_PROGRESS).getStateId()),
                        new SQLPredicate("speaker_id", Operation.EQUALS,
                                  user.getUserId())));
    }

    public List<Meetup> notEnoughAttendees1Hour() throws NotFoundException {
        return find(new SQLPredicate(Operation.AND,
                        new SQLPredicate("start_date_time", Operation.LESS,
                                "NOW() + INTERVAL 1 HOUR"),
                        new SQLPredicate("state_id", Operation.EQUALS,
                                    stateService.get(State.SCHEDULED).getStateId()),
                        new SQLPredicate("min_attendees", Operation.GREATER,
                                "registered_attendees")));
    }

    public List<Meetup> goingToStart() throws NotFoundException {
        return find(new SQLPredicate(Operation.AND,
                new SQLPredicate("state_id", Operation.IN,
                                    stateService.get(State.SCHEDULED).getStateId(),
                                    stateService.get(State.BOOKED).getStateId()),
                new SQLPredicate("start_date_time", Operation.LESS,
                                "NOW()")));
    }

    public List<Meetup> goingToFinish() throws NotFoundException {
        return find(new SQLPredicate(Operation.AND,
                        new SQLPredicate("finish_date_time", Operation.LESS,
                                "NOW()"),
                        new SQLPredicate("state_id", Operation.EQUALS,
                                    stateService.get(State.IN_PROGRESS).getStateId())));
    }

    public List<Meetup> findBy(Filter filter) {
        List<SQLPredicate> andList = new LinkedList<>();
        List<Integer> topicIds = topicService.getIdListFromTopicList(filter.getTopics());

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

        if (isNotEmpty(topicIds))
            andList.add(new SQLPredicate(Operation.EXISTS,
                        new SQLSelect("meetup_topic", "topic_id",
                        new SQLPredicate(Operation.AND, Arrays.asList(
                                                new SQLPredicate("meetup_id", Operation.EQUALS, "uid"),
                                                new SQLPredicate("topic_id", Operation.IN, topicIds)
                                                )))));

        SQLPredicate where = new SQLPredicate(Operation.AND, andList);
        return jdbcTemplate.query(GET_ALL_QUERY + " WHERE " + where.toString(), new MeetupRowMapper());
    }

}