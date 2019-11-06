package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StateDAO implements RowMapper<State> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public State get(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM state WHERE uid = ?", new Object[]{id}, State.class);
    }

    public Map<String, State> getAll() {
        return jdbcTemplate.query("SELECT * FROM state", this)
                .stream().collect(Collectors.toMap(State::getName, s -> s));
    }

    @Override
    public State mapRow(ResultSet resultSet, int i) throws SQLException {
        return new State(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }
}
