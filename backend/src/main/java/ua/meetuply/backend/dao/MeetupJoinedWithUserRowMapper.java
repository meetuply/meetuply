package ua.meetuply.backend.dao;

import ua.meetuply.backend.model.Meetup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetupJoinedWithUserRowMapper extends  MeetupRowMapper{

    @Override
    public Meetup mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meetup meetup = super.mapRow(rs, rowNum);
        meetup.setSpeakerFirstName(rs.getString("firstName"));
        meetup.setSpeakerLastName(rs.getString("surname"));
        meetup.setRating(rs.getFloat("rating"));
        meetup.setSpeakerPhoto(rs.getString("photo"));
        return meetup;
    }
}
