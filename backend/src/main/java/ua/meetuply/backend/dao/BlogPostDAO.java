package ua.meetuply.backend.dao;

import java.security.Principal;
import java.util.*;
import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.controller.MainController;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.model.AppUser;

@Repository
public class BlogPostDAO {

    private static final Map<Long, BlogPost> BLOG_POST_MAP = new HashMap<>();


    public Long getMaxBlogPostId() {
        long max = 0;
        for (Long id : BLOG_POST_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

    public BlogPost findBlogPostByTitle(String title) {
        Collection<BlogPost> bp = BLOG_POST_MAP.values();
        for (BlogPost b : bp) {
            if (b.getBlogPostTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }

    //TODO: implement methods for searching users by name + surname (User)
    public BlogPost findBlogPostsByAuthor(AppUser author) {
        Collection<BlogPost> bp = BLOG_POST_MAP.values();
        for (BlogPost b : bp) {
            if (b.getAuthor().equals(author)) {
                return b;
            }
        }
        return null;
    }

    public List<BlogPost> getBlogPosts() {
        List<BlogPost> list = new ArrayList<>();

        list.addAll(BLOG_POST_MAP.values());
        return list;
    }

    public BlogPost createBlogPost(BlogPostForm form) {
        Long blogPostId = this.getMaxBlogPostId() + 1;

        String email="";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        BlogPost bp = new BlogPost(blogPostId, form.getBlogPostTitle(), //
                form.getBlogPostContent(), LocalDateTime.now(), AppUserDAO.instance.findAppUserByEmail(email));

        BLOG_POST_MAP.put(blogPostId, bp);
        return bp;
    }

}

