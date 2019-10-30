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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BlogPostService blogPostService;

    @Autowired
    AppUserService appUserService;

    @Override
    public BlogComment get(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment WHERE uid = ?", new Object[] { id }, this);
        return blogComments.size() == 0 ? null : blogComments.get(0);
    }

    public List<BlogComment> getByPostId(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment WHERE post_id = ?", new Object[] { id }, this);
        return blogComments;
    }

    @Override
    public List<BlogComment> getAll() {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment", this);
        return blogComments;
    }

    @Override
    public void save(BlogComment blogComment) {
        jdbcTemplate.update(
                "INSERT INTO `comment` (`date_time`, `content`, `post_id`, `author`) " +
                        "VALUES (?, ?, ?, ?)",
                blogComment.getTime(),
                blogComment.getBlogCommentContent(),
                blogComment.getPostId(),
                blogComment.getAuthorId());
    }

    @Override
    public void update(BlogComment blogComment) {
        jdbcTemplate.update("UPDATE comment SET content = ? WHERE uid = ?", blogComment.getBlogCommentContent(), blogComment.getBlogCommentId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM comment WHERE uid = ?", id);
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
