package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Meetup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Repository
public class MeetupDAO implements IDAO<Meetup>, RowMapper<Meetup> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

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

    //todo change userId to function
    @Override
    public void save(Meetup meetup) {
        jdbcTemplate.update("INSERT INTO meetup (`uid`,`place`, `title`, `registered_attendees`, `min_attendees`, `max_attendees`," +
                        "`start_date_time`, `finish_date_time`, `state_id`, `speaker_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", null,
                meetup.getMeetupPlace(),
                meetup.getMeetupTitle(), meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId());
    }

    @Override
    public void update(Meetup meetup) {
        jdbcTemplate.update("UPDATE meetup SET place = ?, " +
                "title = ?, registered_attendees = ?, min_attendees = ?," +
                "max_attendees = ?, start_date_time = ?, finish_date_time = ?," +
                "state_id = ?, speaker_id = ? WHERE uid = ?", meetup.getMeetupPlace(), meetup.getMeetupTitle(),
                meetup.getMeetupRegisteredAttendees(), meetup.getMeetupMinAttendees(), meetup.getMeetupMaxAttendees(),
                meetup.getMeetupStartDateTime(), meetup.getMeetupFinishDateTime(), meetup.getStateId(), meetup.getSpeakerId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM meetup WHERE uid = ?", id);
    }

    public Meetup mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meetup meetup = new Meetup();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        meetup.setMeetupId(rs.getInt("uid"));
        meetup.setMeetupPlace(rs.getString("place"));
        meetup.setMeetupTitle(rs.getString("title"));
        meetup.setMeetupRegisteredAttendees(rs.getInt("registered_attendees"));
        meetup.setMeetupMinAttendees(rs.getInt("min_attendees"));
        meetup.setMeetupMaxAttendees(rs.getInt("max_attendees"));
        meetup.setMeetupStartDateTime(LocalDateTime.parse(rs.getString("start_date_time"), formatter));
        meetup.setMeetupFinishDateTime(LocalDateTime.parse(rs.getString("finish_date_time"), formatter));
        meetup.setStateId(rs.getInt("state_id"));
        meetup.setSpeakerId(rs.getInt("speaker_id"));
        return meetup;
    }
}