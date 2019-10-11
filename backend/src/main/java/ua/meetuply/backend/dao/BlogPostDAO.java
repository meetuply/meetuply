package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.BlogPost;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class BlogPostDAO implements IDAO<BlogPost>, RowMapper<BlogPost> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BlogPost get(Integer id) {
        List<BlogPost> blogPosts = jdbcTemplate.query("SELECT * FROM post WHERE uid = ?", new Object[] { id }, this);
        return blogPosts.size() == 0 ? null : blogPosts.get(0);
    }

    @Override
    public List<BlogPost> getAll() {
        List<BlogPost> blogPosts = jdbcTemplate.query("SELECT * FROM post", this);
        return blogPosts;
    }

    @Override
    public void save(BlogPost blogPost) {
        jdbcTemplate.update(
                "INSERT INTO `post` (`title`, `date_time`, `content`, `author_id`) " +
                        "VALUES (?, ?, ?, ?)",
                blogPost.getBlogPostTitle(), blogPost.getTime(), blogPost.getBlogPostContent(), blogPost.getAuthor().getUserId());
    }

    @Override
    public void update(BlogPost blogPost) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public BlogPost mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BlogPost blogPost = new BlogPost();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        blogPost.setBlogPostId(resultSet.getInt("uid"));
        blogPost.setBlogPostTitle(resultSet.getString("title"));
        blogPost.setBlogPostContent(resultSet.getString("content"));
        blogPost.setTime(resultSet.getTimestamp("date_time").toLocalDateTime());
        blogPost.setAuthor(null); //must be fixed according to new system
//                appUserDAO.findAppUserById(resultSet.getInt("author_id"))
        return blogPost;
    }
}