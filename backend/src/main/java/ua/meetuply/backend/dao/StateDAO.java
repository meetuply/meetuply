package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StateDAO implements RowMapper<State> {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private List<State> states;

    public void load() {
        states = jdbcTemplate.query("SELECT * FROM state", this);
    }

    public State get(Integer id) {
        if (states == null) load();
        return states.stream().filter(s -> s.getStateId() == id).findFirst().orElse(null);
    }

    public State get(String name) {
        if (states == null) load();
        return states.stream().filter(s -> s.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public State mapRow(ResultSet resultSet, int i) throws SQLException {
        return new State(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }

    public Iterable<State> getAll() {
        return states;
    }
}
