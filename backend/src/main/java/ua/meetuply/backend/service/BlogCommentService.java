package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BlogCommentDAO;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.model.BlogComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlogCommentService {

    @Autowired
    BlogCommentDAO blogCommentDAO;

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    AppUserService appUserService;

    public void createBlogComment(BlogComment blogComment, Integer blogPostId) {
        blogComment.setTime(LocalDateTime.now());
        blogComment.setAuthor(appUserService.getUser(appUserService.getCurrentUserID()));
        blogComment.setPost(blogPostDAO.get(blogPostId));
        blogCommentDAO.save(blogComment);
    }

    public void updateBlogComment(BlogComment blogComment){
        blogCommentDAO.update(blogComment);
    }

    public void deleteBlogComment(Integer id){
        blogCommentDAO.delete(id);
    }

    public List<BlogComment> getBlogComments() {
        return blogCommentDAO.getAll();
    }

    public List<BlogComment> getBlogCommentsByPostId(Integer id) {
        return blogCommentDAO.getByPostId(id);
    }

    public BlogComment getBlogCommentById(Integer id) {return blogCommentDAO.get(id);}
}
