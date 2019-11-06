package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.AppUserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BlogPostDAO implements IDAO<BlogPost>, RowMapper<BlogPost> {

    private static final String GET_ALL_QUERY = "SELECT * FROM post";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM post WHERE uid = ?";
    private static final String SAVE_QUERY = "INSERT INTO `post` (`title`, `date_time`, `content`, `author_id`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM post WHERE uid = ?";
    private static final String UPDATE_QUERY = "UPDATE post SET content = ? AND title = ? WHERE uid = ?";

    private static final String FIND_POSTS_BY_FILTER_ALL = "SELECT * FROM post WHERE author_id IN (SELECT uid FROM user WHERE is_deactivated=0) order by uid desc LIMIT ?, ?";
    private static final String FIND_POSTS_BY_FILTER_MY = "SELECT * FROM post WHERE author_id = ? order by uid desc LIMIT ?, ? ";
    private static final String FIND_POSTS_BY_FILTER_SUBS = "SELECT * FROM post WHERE author_id IN (:subs) order by uid desc LIMIT :startRow, :endRow";
    private static final String FIND_POSTS_BY_USER_ID = "SELECT * FROM post WHERE author_id=? order by uid desc LIMIT ?, ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public BlogPost get(Integer id) {
        List<BlogPost> blogPosts = jdbcTemplate.query(GET_BY_ID_QUERY, new Object[]{id}, this);
        return blogPosts.size() == 0 ? null : blogPosts.get(0);
    }

    public Iterable<BlogPost> getBlogPostByUserId(Integer startRow,Integer endRow,Integer userId) {
        return jdbcTemplate.query(FIND_POSTS_BY_USER_ID, new Object[]{userId,startRow,endRow}, this);
    }

    @Override
    public List<BlogPost> getAll() {
        return jdbcTemplate.query(GET_ALL_QUERY, this);
    }

    public List<BlogPost> getBlogPostsChunk(Integer startRow, Integer endRow,String filter) {
        switch (filter){
            case "subs":
                List<AppUser> subs = appUserService.getUserSubscriptionsUsers(appUserService.getCurrentUserID());
                subs.removeIf(u -> u.isDeactivated());
                List<Integer> subsids = subs.stream().map(s -> s.getUserId()).collect(Collectors.toList());
                if (subs.size()>=1){
                    MapSqlParameterSource parameters = new MapSqlParameterSource();
                    parameters.addValue("subs", subsids);
                    parameters.addValue("startRow", startRow);
                    parameters.addValue("endRow", endRow);

                    return namedParameterJdbcTemplate.query(FIND_POSTS_BY_FILTER_SUBS, parameters, this);
                }
                else return new ArrayList<BlogPost>();
            case "my":
                return jdbcTemplate.query(FIND_POSTS_BY_FILTER_MY, new Object[]{appUserService.getCurrentUserID(), startRow, endRow}, this);
            case "all":
            default:
                return jdbcTemplate.query(FIND_POSTS_BY_FILTER_ALL, new Object[]{startRow, endRow}, this);
        }
    }

    @Override
    public void save(BlogPost blogPost) {
        jdbcTemplate.update(
                SAVE_QUERY,
                blogPost.getBlogPostTitle(),
                blogPost.getTime(),
                blogPost.getBlogPostContent(),
                blogPost.getAuthorId());
    }

    @Override
    public void update(BlogPost blogPost) {
        jdbcTemplate.update(UPDATE_QUERY, blogPost.getBlogPostContent(), blogPost.getBlogPostTitle(), blogPost.getBlogPostId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public BlogPost mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BlogPost blogPost = new BlogPost();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        blogPost.setBlogPostId(resultSet.getInt("uid"));
        blogPost.setBlogPostTitle(resultSet.getString("title"));
        blogPost.setBlogPostContent(resultSet.getString("content"));
        blogPost.setTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        blogPost.setAuthorId(resultSet.getInt("author_id"));
        return blogPost;
    }


}
