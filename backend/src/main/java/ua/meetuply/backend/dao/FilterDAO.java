package ua.meetuply.backend.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Topic;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Component
public class FilterDAO implements IFilterDAO<Filter>, RowMapper<Filter> {

    private static final String CREATE_FILTER_QUERY = "INSERT INTO `saved_filter` " +
            "(`name`, `rating_from`, `rating_to`,`date_time_from`, `date_time_to`, `owner_id`) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_FILTER_TOPIC_QUERY = "INSERT INTO `filter_topic` (`topic_id`, `filter_id`) VALUES (?, ?)";
    private static final String GET_FILTER_QUERY = "SELECT * FROM saved_filter WHERE uid = ?";
    private static final String GET_FILTERS_TOPICS_QUERY = "SELECT `topic_id` FROM `filter_topic` WHERE filter_id = ?";
    private static final String GET_USERS_FILTERS_QUERY = "SELECT * FROM saved_filter WHERE owner_id = ?";

    @Resource
    private IDAO<Topic> topicDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Filter get(Integer id) {
        List<Filter> filters = jdbcTemplate.query(GET_FILTER_QUERY, new Object[]{id}, this);
        return filters.size() == 0 ? null : filters.get(0);
    }

    @Override
    public List<Filter> getUsersFilters(Integer userId){
        return jdbcTemplate.query(GET_USERS_FILTERS_QUERY, new Object[]{userId}, this);
    }

    private List<Integer> getTopicIds(Integer filterId) {
        return jdbcTemplate.queryForList(GET_FILTERS_TOPICS_QUERY, new Object[]{filterId}, Integer.class);
    }

    @Override
    public List<Filter> getAll() {
        return jdbcTemplate.query("SELECT * FROM saved_filter",this);
    }

    @Override
    public void save(Filter filter) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(CREATE_FILTER_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, filter.getName());
            ps.setObject(2, filter.getRatingFrom());
            ps.setObject(3, filter.getRatingTo());
            ps.setTimestamp(4, filter.getDateFrom());
            ps.setTimestamp(5, filter.getDateTo());
            ps.setInt(6, filter.getUserId());
            return ps;
        }, keyHolder);

        int key =  keyHolder.getKey().intValue();


//        jdbcTemplate.update(CREATE_FILTER_QUERY, filter.getName(), filter.getRatingFrom(), filter.getRatingTo(), filter.getDateFrom(),
//                filter.getDateTo(), filter.getUserId());
        if (isNotEmpty(filter.getTopics())) {
            for (Topic topic : filter.getTopics()) {
                jdbcTemplate.update(CREATE_FILTER_TOPIC_QUERY, topic.getTopicId(), key);
            }
        }
    }

    @Override
    public void update(Filter filter) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Filter mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer filterId = resultSet.getInt("uid");
        List<Topic> topics = getTopicIds(filterId).stream()
                .map(id -> topicDao.get(id))
                .collect(toList());

        return new Filter(
                filterId,
                resultSet.getString("name"),
                resultSet.getObject("rating_from") != null ? resultSet.getFloat("rating_from") : null,
                resultSet.getObject("rating_to") != null ? resultSet.getFloat("rating_to") : null,
                resultSet.getTimestamp("date_time_from"),
                resultSet.getTimestamp("date_time_to"),
                resultSet.getInt("owner_id"),
                topics
                );
    }
}
