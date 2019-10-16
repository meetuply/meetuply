package ua.meetuply.backend.model;

import java.time.LocalDateTime;

public class BlogPost {

    private Integer blogPostId;
    private String blogPostTitle;
    private String blogPostContent;
    private LocalDateTime time;
    private AppUser author;

    public BlogPost() {

    }

    public BlogPost(String blogPostTitle, String blogPostContent, LocalDateTime time, AppUser author) {
        super();
        this.blogPostTitle=blogPostTitle;
        this.blogPostContent = blogPostContent;
        this.time = time;
        this.author = author;
    }

    public Integer getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Integer blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getBlogPostTitle() {
        return blogPostTitle;
    }

    public void setBlogPostTitle(String blogPostTitle) {
        this.blogPostTitle = blogPostTitle;
    }

    public String getBlogPostContent() {
        return blogPostContent;
    }

    public void setBlogPostContent(String blogPostContent) {
        this.blogPostContent = blogPostContent;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }
}