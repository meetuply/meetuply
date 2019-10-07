package ua.meetuply.backend.blog.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import ua.meetuply.backend.blog.formbean.BlogPostForm;
import ua.meetuply.backend.blog.model.BlogPost;
import ua.meetuply.backend.blog.model.User;

@Repository
public class BlogPostDAO {

    private static final Map<Long, BlogPost> BLOG_POST_MAP = new HashMap<>();

    static {
        initDATA();
    }

    private static void initDATA() {
        BlogPost a = new BlogPost(1L, "This is a new post", "Lorem ipsum", LocalDateTime.now(), new User(1L, "tom", "Tom", "Tom", //
                true, "tom@waltdisney.com", "US"));

        BlogPost b = new BlogPost(2L, "HKGjdhfsbd", "Hfbjdkndsfjkdbdghjkslfdbgfsnjf", LocalDateTime.now(), new User(2L, "jerry", "Jerry", "Jerry", //
                true, "jerry@waltdisney.com", "US"));

        BLOG_POST_MAP.put(a.getBlogpostId(), a);
        BLOG_POST_MAP.put(b.getBlogpostId(), b);
    }

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
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        return null;
    }

    //TODO: implement methods for searching users by name + surname (User)
    public BlogPost findAppUserByAuthor(User author) {
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
        Long blogpostId = this.getMaxBlogPostId() + 1;

        BlogPost bp = new BlogPost(blogpostId, form.getTitle(), //
                form.getContent(), form.getTime(), form.getAuthor());

        BLOG_POST_MAP.put(blogpostId, bp);
        return bp;
    }

}

