package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.model.BlogPost;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BlogPostService {

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    AppUserService appUserService;

    public void createBlogPost(BlogPost blogPost) {
        blogPost.setTime(LocalDateTime.now());
        blogPost.setAuthorId(appUserService.getCurrentUserID());
        blogPostDAO.save(blogPost);
    }

    public void updateBlogPost(BlogPost blogPost){
        blogPostDAO.update(blogPost);
    }

    public void deleteBlogPost(Integer id){
        blogPostDAO.delete(id);
    }

    public List<BlogPost> getBlogPosts() {
        return blogPostDAO.getAll();
    }

    public BlogPost getBlogPostById(Integer id) {return blogPostDAO.get(id);}

    public List<BlogPost> getBlogPostsChunk(Integer startRow,Integer endRow) {
        return blogPostDAO.getBlogPostsChunk(startRow,endRow);
    }
}
