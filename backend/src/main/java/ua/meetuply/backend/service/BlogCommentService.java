package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BlogCommentDAO;
import ua.meetuply.backend.model.BlogComment;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlogCommentService {

    @Autowired
    BlogCommentDAO blogCommentDAO;

    public void createBlogComment(BlogComment blogComment) {
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
        List<BlogComment> blogComments = new ArrayList<>();
        for (BlogComment bc : blogCommentDAO.getAll()){
            if (bc.getPost().getBlogPostId()==id)
                blogComments.add(bc);
        }

        return blogComments;
    }

    public BlogComment getBlogCommentById(Integer id) {return blogCommentDAO.get(id);}
}
