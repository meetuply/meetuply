package ua.meetuply.backend.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.model.Filter;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilterDAO implements IDAO<Filter>, RowMapper<Filter> {

    private static final String CREATE_FILTER_QUERY = "INSERT INTO `saved_filter` " +
            "(`name`,`rating_from`, `rating_to`,`date_time_from`, `date_time_to`, `owner_id`) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_FILTER_QUERY = "SELECT * FROM saved_filter WHERE uid = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Filter get(Integer id) {
        List<Filter> filters = jdbcTemplate.query(GET_FILTER_QUERY, new Object[]{id}, this);
        return filters.size() == 0 ? null : filters.get(0);
    }

    public List<Integer> getTopicsIds(Integer filterId) {
        return jdbcTemplate.queryForList("SELECT topic_id FROM meetuply.filter_topic WHERE filter_id = ?", new Object[]{filterId}, Integer.class);
    }

    @Override
    public List<Filter> getAll() {
        return jdbcTemplate.query("SELECT * FROM saved_filter",this);
    }

    @Override
    public void save(Filter filter) {
        jdbcTemplate.update(CREATE_FILTER_QUERY, filter.getName(), filter.getDateFrom(), filter.getRatingTo(), filter.getDateFrom(),
                filter.getDateTo(), filter.getUserId());
    }

    @Override
    public void update(Filter filter) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Filter mapRow(ResultSet resultSet, int i) throws SQLException {

        return new Filter(
                resultSet.getInt("uid"),
                resultSet.getString("name"),
                resultSet.getObject("rating_from") != null ? resultSet.getDouble("rating_from") : null,
                resultSet.getObject("rating_to") != null ? resultSet.getDouble("rating_to") : null,
                resultSet.getTimestamp("date_time_from"),
                resultSet.getTimestamp("date_time_to"),
                resultSet.getInt("owner_id"),
                getTopicsIds(resultSet.getInt("uid"))
        );
    }
}
