package ua.meetuply.backend.model;

import java.time.LocalDateTime;

public class BlogComment {

    private Long blogCommentId;
    private String blogCommentContent;
    private LocalDateTime time;

    private BlogPost post;

    private AppUser author;

    public BlogComment() {

    }

    public BlogComment(Long blogCommentId, String blogCommentContent, LocalDateTime time, BlogPost post, AppUser author) {
        super();
        this.blogCommentId = blogCommentId;
        this.blogCommentContent=blogCommentContent;
        this.time = time;
        this.post=post;
        this.author = author;
    }

    public Long getBlogCommentId() {
        return blogCommentId;
    }

    public void setBlogCommentId(Long blogCommentId) {
        this.blogCommentId = blogCommentId;
    }

    public String getBlogCommentContent() {
        return blogCommentContent;
    }

    public void setBlogCommentContent(String blogCommentContent) {
        this.blogCommentContent = blogCommentContent;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public BlogPost getPost() { return post; }

    public void setPost(BlogPost post) { this.post = post; }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthorId(AppUser author) {
        this.author = author;
    }

}
