package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.ConfirmationToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConfirmationTokenDAO implements IDAO<ConfirmationToken>, RowMapper<ConfirmationToken> {

    private static final String SELECT_ALL_QUERY = "SELECT * FROM confirmation_token";
    private static final String SELECT_TOKEN_BY_ID_QUERY = "SELECT * FROM confirmation_token WHERE uid = ?";
    private static final String SELECT_TOKEN_BY_TOKEN = "SELECT * FROM confirmation_token WHERE token = ?";

    private static final String INSERT_TOKEN_QUERY =
            "INSERT INTO `confirmation_token` (`token`, `create_date`, `user_id`) " +
                    "VALUES (?, ?, ?)";

    private static final String UPDATE_TOKEN_QUERY =
            "UPDATE confirmation_token SET token = ? AND create_date = ? AND user_id = ? WHERE uid = ?";

    private static final String DELETE_TOKEN_QUERY =
            "DELETE FROM confirmation_token WHERE uid = ?";

    @Autowired
    AppUserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ConfirmationToken get(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_TOKEN_BY_ID_QUERY, new Object[]{id}, this);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public ConfirmationToken getByToken(String token) {
        try {
            return jdbcTemplate.queryForObject(SELECT_TOKEN_BY_TOKEN, new Object[]{token}, this);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ConfirmationToken> getAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this);
    }

    @Override
    public void save(ConfirmationToken ct) {
        jdbcTemplate.update(INSERT_TOKEN_QUERY,
                ct.getConfirmationToken(), ct.getCreatedDate(), ct.getUser().getUserId()
        );
    }

    @Override
    public void update(ConfirmationToken ct) {
        jdbcTemplate.update(UPDATE_TOKEN_QUERY,
                ct.getConfirmationToken(), ct.getCreatedDate(), ct.getUser().getUserId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_TOKEN_QUERY, id);
    }

    @Override
    public ConfirmationToken mapRow(ResultSet resultSet, int i) throws SQLException {
        ConfirmationToken ct = new ConfirmationToken(
                resultSet.getInt("uid"),
                resultSet.getString("token"),
                resultSet.getDate("create_date"),
                userDAO.get(resultSet.getInt("user_id"))
        );
        return ct;
    }
}
