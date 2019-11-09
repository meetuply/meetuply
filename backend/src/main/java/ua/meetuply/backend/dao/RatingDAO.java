package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.Rating;
import ua.meetuply.backend.service.AppUserService;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RatingDAO implements IDAO<Rating>, RowMapper<Rating> {

    private static final String GET_ALL_QUERY = "SELECT * FROM rating";
    private static final String GET_BY_IDS_QUERY = "SELECT * FROM rating WHERE rated_user_id = ? AND rated_by = ?";
    private static final String SAVE_QUERY = "INSERT INTO `rating` (`rated_user_id`, `rated_by`, `value`, `date_time`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM rating WHERE rated_user_id = ? AND rated_by = ?";
    private static final String UPDATE_QUERY = "UPDATE rating SET value = ?, date_time = ? WHERE rated_user_id = ? AND rated_by = ?";

    private static final String GET_BY_RATED_USER = "SELECT * FROM rating WHERE rated_user_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserService appUserService;

    @Override
    public Rating get(Integer id) {
//        return null;
        try {
            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Rating getByUserIds(Integer idby, Integer idrated) {
        List<Rating> ratings = jdbcTemplate.query(GET_BY_IDS_QUERY, new Object[]{idrated, idby}, this);
        return ratings.size() == 0 ? null : ratings.get(0);
    }

    public List<Rating> getByRatedUserId(Integer id) {
        return jdbcTemplate.query(GET_BY_RATED_USER, new Object[]{id}, this);
    }

    @Override
    public List<Rating> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, this);
    }

    @Override
    public void save(Rating rating) {
        jdbcTemplate.update(SAVE_QUERY,
                rating.getRatedUser(),
                rating.getRatedBy(),
                rating.getValue(),
                rating.getDate());
    }

    @Override
    public void update(Rating rating) {
        jdbcTemplate.update(UPDATE_QUERY,rating.getValue(), rating.getDate(), rating.getRatedUser(), rating.getRatedBy());
    }

    @Override
    public void delete(Integer id) {
        try {
            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUserIds(Integer idby, Integer idrated) {
        jdbcTemplate.update(DELETE_QUERY, new Object[]{idrated, idby}, this);
    }

    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        rating.setValue(resultSet.getInt("value"));
        rating.setDate(resultSet.getTimestamp("date_time").toLocalDateTime());
        rating.setRatedUser(resultSet.getInt("rated_user_id"));
        rating.setRatedBy(resultSet.getInt("rated_by"));
        return rating;
    }
}
