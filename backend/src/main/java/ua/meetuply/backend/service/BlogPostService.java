package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.BlogPost;

import java.util.List;

@Component
public class BlogPostService {

    @Autowired
    BlogPostDAO blogPostDAO;

    public BlogPost createBlogPost(BlogPostForm form) {
        String email="";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        //todo: get user id and find user using service

        BlogPost blogPost = new BlogPost(form.getBlogPostTitle(),
                form.getBlogPostContent(), form.getTime(), null); //need to implement author
        blogPostDAO.save(blogPost);
        return blogPost;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPostDAO.getAll();
    }

    public BlogPost getBlogPostById(Integer id) {return blogPostDAO.get(id);}
}