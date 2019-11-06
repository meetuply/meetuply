package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.service.AppUserService;
import ua.meetuply.backend.service.BlogPostService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class BlogCommentDAO implements IDAO<BlogComment>, RowMapper<BlogComment> {

    private static final String GET_ALL_QUERY = "SELECT * FROM comment";
    private static final String GET_BY_ID_QUERY = "SELECT * FROM comment WHERE uid = ?";
    private static final String SAVE_QUERY = "INSERT INTO `comment` (`date_time`, `content`, `post_id`, `author`) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM comment WHERE uid = ?";
    private static final String UPDATE_QUERY = "UPDATE comment SET content = ? WHERE uid = ?";

    private static final String GET_CHUNK_QUERY ="SELECT * FROM comment WHERE post_id = ? order by uid desc LIMIT ?, ?";

    private static final String FIND_BY_POST_ID_QUERY = "SELECT * FROM comment WHERE post_id = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BlogPostService blogPostService;

    @Autowired
    AppUserService appUserService;

    @Override
    public BlogComment get(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query(GET_BY_ID_QUERY, new Object[] { id }, this);
        return blogComments.size() == 0 ? null : blogComments.get(0);
    }

    public List<BlogComment> getByPostId(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query(FIND_BY_POST_ID_QUERY, new Object[] { id }, this);
        return blogComments;
    }

    @Override
    public List<BlogComment> getAll() {
        List<BlogComment> blogComments = jdbcTemplate.query(GET_ALL_QUERY, this);
        return blogComments;
    }

    public List<BlogComment> getBlogCommentsChunk(Integer postId, Integer startRow, Integer endRow) {
        return jdbcTemplate.query(GET_CHUNK_QUERY, new Object[]{postId, startRow, endRow}, this);
    }

    @Override
    public void save(BlogComment blogComment) {
        jdbcTemplate.update(SAVE_QUERY,
                blogComment.getTime(),
                blogComment.getBlogCommentContent(),
                blogComment.getPostId(),
                blogComment.getAuthorId());
    }

    @Override
    public void update(BlogComment blogComment) {
        jdbcTemplate.update(UPDATE_QUERY, blogComment.getBlogCommentContent(), blogComment.getBlogCommentId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public BlogComment mapRow(ResultSet resultSet, int i) throws SQLException {
        BlogComment blogComment = new BlogComment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        blogComment.setBlogCommentId(resultSet.getInt("uid"));
        blogComment.setBlogCommentContent(resultSet.getString("content"));
        blogComment.setTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        blogComment.setPostId(resultSet.getInt("post_id"));
        blogComment.setAuthorId(resultSet.getInt("author"));
        return blogComment;
    }


}
