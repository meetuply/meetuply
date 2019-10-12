package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BlogCommentDAO;
import ua.meetuply.backend.formbean.BlogCommentForm;
import ua.meetuply.backend.model.BlogComment;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlogCommentService {

    @Autowired
    BlogCommentDAO blogCommentDAO;

    public BlogComment createBlogComment(BlogCommentForm form) {
        String email="";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        //todo: get user id and find user using service

        BlogComment blogComment = new BlogComment(form.getBlogCommentContent(),
                form.getTime(), null, null); //need to implement author and post
        blogCommentDAO.save(blogComment);
        return blogComment;
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
