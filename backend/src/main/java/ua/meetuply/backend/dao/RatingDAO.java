package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Rating;
import ua.meetuply.backend.service.AppUserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RatingDAO implements IDAO<Rating>, RowMapper<Rating> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserService appUserService;

    @Override
    public Rating get(Integer id) {
        return null;
    }

    public Rating getByUserIds(Integer idby, Integer idrated) {
        List<Rating> ratings = jdbcTemplate.query("SELECT * FROM rating WHERE rated_user_id = ? AND rated_by = ?", new Object[]{idrated, idby}, this);
        return ratings.size() == 0 ? null : ratings.get(0);
    }

    public List<Rating> getByRatedUserId(Integer id) {
        List<Rating> ratings = jdbcTemplate.query("SELECT * FROM rating WHERE rated_user_id = ?", new Object[]{id}, this);
        return ratings;
    }

    @Override
    public List<Rating> getAll() {
        List<Rating> ratings = jdbcTemplate.query("SELECT * FROM rating", this);
        return ratings;
    }

    @Override
    public void save(Rating rating) {
        jdbcTemplate.update(
                "INSERT INTO `rating` (`rated_user_id`, `rated_by`, `value`, `date_time`) " +
                        "VALUES (?, ?, ?, ?)",
                rating.getRatedUser().getUserId(),
                rating.getRatedBy().getUserId(),
                rating.getValue(),
                rating.getDate());
    }

    @Override
    public void update(Rating rating) {
        jdbcTemplate.update("UPDATE rating SET value = ? AND date_time = ? WHERE rated_user_id = ? AND rated_by = ?",
                rating.getValue(), rating.getDate(), rating.getRatedUser().getUserId(), rating.getRatedUser().getUserId());
    }

    @Override
    public void delete(Integer id) {
    }

    public void deleteByUserIds(Integer idby, Integer idrated) {
        jdbcTemplate.update("DELETE FROM rating WHERE rated_user_id = ? AND rated_by = ?", new Object[]{idrated, idby}, this);
    }

    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        rating.setValue(resultSet.getInt("value"));
        rating.setDate(resultSet.getTimestamp("date_time").toLocalDateTime());
        rating.setRatedUser(appUserService.getUser(resultSet.getInt("rated_user_id")));
        rating.setRatedBy(appUserService.getUser(resultSet.getInt("rated_by")));
        return rating;
    }
}
