package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.meetuply.backend.dao.BlogPostDAO;
import ua.meetuply.backend.model.AchievementType;
import ua.meetuply.backend.model.BlogPost;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BlogPostService {

    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    AppUserService appUserService;

    @Autowired
    AchievementService achievementService;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void createBlogPost(BlogPost blogPost) {
        blogPost.setTime(LocalDateTime.now());
        blogPost.setAuthorId(appUserService.getCurrentUserID());
        blogPostDAO.save(blogPost);

        for (Integer user: appUserService.getUserSubscribers(appUserService.getCurrentUserID())) {
            notificationService.sendNotification(user,"subscription_new_post_template");
        }

        achievementService.checkOne(AchievementType.POSTS);
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

    public List<BlogPost> getBlogPostsChunk(Integer startRow,Integer endRow,String filter) {
        return blogPostDAO.getBlogPostsChunk(startRow,endRow,filter);
    }

    public Iterable<BlogPost> getBlogPostByUserId(Integer startRow,Integer endRow,Integer userId) {
        return blogPostDAO.getBlogPostByUserId(startRow,endRow,userId);
    }
}
