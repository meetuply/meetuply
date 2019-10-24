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
public class FilterDao implements IDAO<Filter>, RowMapper<Filter> {

    private static final String CREATE_FILTER_QUERY = "INSERT INTO `saved_filter` " +
            "(`name`, `rating`,`date_time_from`, `date_time_to`, `owner_id`) VALUES (?, ?, ?, ?, ?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Filter get(Integer id) {
        return null;
    }

    @Override
    public List<Filter> getAll() {
        return null;
    }

    @Override
    public void save(Filter filter) {
        jdbcTemplate.update(CREATE_FILTER_QUERY, filter.getName(), filter.getRating(), filter.getDateFrom(),
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
        return null;
    }
}
