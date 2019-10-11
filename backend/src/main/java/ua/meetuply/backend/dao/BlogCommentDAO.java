package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.formbean.BlogCommentForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.BlogPostService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class BlogCommentDAO implements IDAO<BlogComment>, RowMapper<BlogComment> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BlogPostService blogPostService;

    @Override
    public BlogComment get(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment WHERE uid = ?", new Object[] { id }, this);
        return blogComments.size() == 0 ? null : blogComments.get(0);
    }

    @Override
    public List<BlogComment> getAll() {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment", this);
        return blogComments;
    }

    @Override
    public void save(BlogComment blogComment) {
        jdbcTemplate.update(
                "INSERT INTO `post` (`date_time`, `content`, `post_id`, `author`) " +
                        "VALUES (?, ?, ?, ?)",
                blogComment.getTime(), blogComment.getBlogCommentContent(), blogComment.getPost().getBlogPostId(), blogComment.getAuthor().getUserId());
    }

    @Override
    public void update(BlogComment blogComment) {

    }

    @Override
    public void delete(Integer id) {

    }

    public List<BlogComment> getCommentsByPostId(Integer id) {
        List<BlogComment> blogComments = jdbcTemplate.query("SELECT * FROM comment WHERE post_id = ?", new Object[] { id }, this);
        return blogComments;
    }

    @Override
    public BlogComment mapRow(ResultSet resultSet, int i) throws SQLException {
        BlogComment blogComment = new BlogComment();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        blogComment.setBlogCommentId(resultSet.getInt("uid"));
        blogComment.setBlogCommentContent(resultSet.getString("content"));
        blogComment.setTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        blogComment.setPost(blogPostService.getBlogPostById(resultSet.getInt("post_id")));
        blogComment.setAuthor(null); //must be fixed according to new system
//                appUserDAO.findAppUserById(resultSet.getInt("author"))
        return blogComment;
    }
}