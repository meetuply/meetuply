package ua.meetuply.backend.dao.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import ua.meetuply.backend.model.Meetup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeetupRowMapper implements RowMapper<Meetup> {

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
}
