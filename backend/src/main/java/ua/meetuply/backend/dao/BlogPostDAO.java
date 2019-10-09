package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogPost;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class BlogPostDAO {

    private static final Map<Long, BlogPost> BLOG_POST_MAP = new HashMap<>();

    @Autowired
    AppUserDAO appUserDAO;

    public Long getMaxBlogPostId() {
        long max = 0;
        for (Long id : BLOG_POST_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

    public List<BlogPost> findBlogPostsByTitle(String title) {
        Collection<BlogPost> bp = BLOG_POST_MAP.values();
        List<BlogPost> res = new ArrayList<>();

        for (BlogPost b : bp) {
            if (b.getBlogPostTitle().equals(title)) {
                res.add(b);
            }
        }
        return res;
    }

    //TODO: implement methods for searching users by name + surname
    public List<BlogPost> findBlogPostsByAuthor(AppUser author) {
        Collection<BlogPost> bp = BLOG_POST_MAP.values();
        List<BlogPost> res = new ArrayList<>();

        for (BlogPost b : bp) {
            if (b.getAuthor().getUserId()==author.getUserId()) {
                res.add(b);
            }
        }
        return res;
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
                form.getBlogPostContent(), LocalDateTime.now(), appUserDAO.findAppUserByEmail(email));

        BLOG_POST_MAP.put(blogPostId, bp);
        return bp;
    }

}

