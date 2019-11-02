package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.AppUserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogPostDAO implements IDAO<BlogPost>, RowMapper<BlogPost> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AppUserService appUserService;

    @Override
    public BlogPost get(Integer id) {
        List<BlogPost> blogPosts = jdbcTemplate.query("SELECT * FROM post WHERE uid = ?", new Object[]{id}, this);
        return blogPosts.size() == 0 ? null : blogPosts.get(0);
    }

    @Override
    public List<BlogPost> getAll() {
        List<BlogPost> blogPosts = jdbcTemplate.query("SELECT * FROM post", this);
        return blogPosts;
    }

    public List<BlogPost> getBlogPostsChunk(Integer startRow, Integer endRow,String filter) {
        switch (filter){
            case "subs":
                List<Integer> subs = appUserService.getUserSubscribers(appUserService.getCurrentUserID());
                if (subs.size()>1){
                    List<String> subsstring = new ArrayList<>(subs.size());
                    for (Integer i : subs) {
                        subsstring.add(String.valueOf(i));
                    }
                    String str="("+String.join(",", subsstring)+")";
                    return jdbcTemplate.query("SELECT * FROM post WHERE author_id IN ? order by uid desc LIMIT ?, ? ", new Object[]{str, startRow, endRow}, this);
                }
                else if (subs.size()==1){
                    return jdbcTemplate.query("SELECT * FROM post WHERE author_id = ? order by uid desc LIMIT ?, ? ", new Object[]{subs.get(0), startRow, endRow}, this);
                }
                else return new ArrayList<BlogPost>();
            case "my":
                return jdbcTemplate.query("SELECT * FROM post WHERE author_id = ? order by uid desc LIMIT ?, ? ", new Object[]{appUserService.getCurrentUserID(), startRow, endRow}, this);
            case "all":
            default:
                return jdbcTemplate.query("SELECT * FROM post order by uid desc LIMIT ?, ?", new Object[]{startRow, endRow}, this);
        }
    }

    @Override
    public void save(BlogPost blogPost) {
        jdbcTemplate.update(
                "INSERT INTO `post` (`title`, `date_time`, `content`, `author_id`) " +
                        "VALUES (?, ?, ?, ?)",
                blogPost.getBlogPostTitle(),
                blogPost.getTime(),
                blogPost.getBlogPostContent(),
                blogPost.getAuthorId());
    }

    @Override
    public void update(BlogPost blogPost) {
        jdbcTemplate.update("UPDATE post SET content = ? AND title = ? WHERE uid = ?",
                blogPost.getBlogPostContent(), blogPost.getBlogPostTitle(), blogPost.getBlogPostId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM post WHERE uid = ?", id);
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
