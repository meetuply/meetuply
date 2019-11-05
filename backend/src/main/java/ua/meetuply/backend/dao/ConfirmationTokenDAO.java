package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.ConfirmationToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConfirmationTokenDAO implements IDAO<ConfirmationToken>, RowMapper<ConfirmationToken> {

    @Autowired
    AppUserDAO userDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ConfirmationToken get(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM confirmation_token WHERE uid = ?", new Object[]{id}, this);

    }

    public ConfirmationToken getByToken(String token) {
        return jdbcTemplate.queryForObject("SELECT * FROM confirmation_token WHERE token = ?", new Object[]{token}, this);
    }

    @Override
    public List<ConfirmationToken> getAll() {
        return jdbcTemplate.query("SELECT * FROM confirmation_token", this);
    }

    @Override
    public void save(ConfirmationToken ct) {
        jdbcTemplate.update(
                "INSERT INTO `confirmation_token` (`token`, `create_date`, `user_id`) " +
                        "VALUES (?, ?, ?)",
                ct.getConfirmationToken(), ct.getCreatedDate(), ct.getUser().getUserId()
        );
    }

    @Override
    public void update(ConfirmationToken ct) {
        jdbcTemplate.update("UPDATE confirmation_token SET token = ? AND create_date = ? AND user_id = ? WHERE uid = ?",
                ct.getConfirmationToken(), ct.getCreatedDate(), ct.getUser().getUserId());
    }

    @Override
    public void delete(Integer id) { jdbcTemplate.update("DELETE FROM confirmation_token WHERE uid = ?", id); }

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
