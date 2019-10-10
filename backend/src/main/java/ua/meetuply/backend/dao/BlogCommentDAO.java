package ua.meetuply.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ua.meetuply.backend.formbean.BlogCommentForm;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.AppUser;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class BlogCommentDAO {

    private static final Map<Long, BlogComment> BLOG_COMMENT_MAP = new HashMap<>();

    @Autowired
    AppUserDAO appUserDAO;

    public Long getMaxBlogCommentId() {
        long max = 0;
        for (Long id : BLOG_COMMENT_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

    public List<BlogComment> findBlogCommentsByAuthor(AppUser author) {
        Collection<BlogComment> bp = BLOG_COMMENT_MAP.values();
        List<BlogComment> res = new ArrayList<>();

        for (BlogComment b : bp) {
            if (b.getAuthor().getUserId()==author.getUserId()) {
                res.add(b);
            }
        }
        return res;
    }

    public List<BlogComment> findBlogCommentsByPost(BlogPost post) {
        Collection<BlogComment> bp = BLOG_COMMENT_MAP.values();
        List<BlogComment> res = new ArrayList<>();

        for (BlogComment b : bp) {
            if (b.getPost().equals(post)) {
                res.add(b);
            }
        }
        return res;
    }

    public List<BlogComment> getBlogComments() {
        List<BlogComment> list = new ArrayList<>();

        list.addAll(BLOG_COMMENT_MAP.values());
        return list;
    }

    public BlogComment createBlogComment(BlogCommentForm form) {
        Long blogCommentId = this.getMaxBlogCommentId() + 1;

        String email="";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        BlogComment bp = new BlogComment(blogCommentId, form.getBlogCommentContent(), //
                LocalDateTime.now(), form.getPost(), appUserDAO.findAppUserByEmail(email));

        BLOG_COMMENT_MAP.put(blogCommentId, bp);
        return bp;
    }

}

