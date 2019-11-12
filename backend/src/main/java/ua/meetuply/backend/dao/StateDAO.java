package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.State;
import ua.meetuply.backend.model.StateImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class StateDAO implements RowMapper<State> {

    private static final String GET_ALL_QUERY   = "SELECT * FROM state";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM state WHERE uid = ?";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public StateImpl get(Integer id) {
        return jdbcTemplate.queryForObject(GET_BY_ID_QUERY, new Object[]{id}, StateImpl.class);
    }

    public Map<String, State> getAll() {
        System.out.println(GET_ALL_QUERY);
        return jdbcTemplate.query(GET_ALL_QUERY, this)
                .stream().collect(Collectors.toMap(State::getName, s -> s));
    }

    @Override
    public State mapRow(ResultSet resultSet, int i) throws SQLException {
        return new StateImpl(
                resultSet.getInt("uid"),
                resultSet.getString("name")
        );
    }
}
