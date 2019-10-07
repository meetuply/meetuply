package ua.meetuply.backend.blog.formbean;

import ua.meetuply.backend.blog.model.User;

import java.time.LocalDateTime;

public class BlogPostForm {
    private Long blogpostId;
    private String title;
    private String content;
    private LocalDateTime time;
    private User author;

    public BlogPostForm() {

    }

    public BlogPostForm(Long blogpostId, String title, String content, LocalDateTime time, User author) {
        super();
        this.blogpostId = blogpostId;
        this.time = time;
        this.content = content;
        this.time = time;
        this.author = author;
    }

    public Long getBlogpostId() {
        return blogpostId;
    }

    public void setBlogpostId(Long blogpostId) {
        this.blogpostId = blogpostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User getAuthor() {
        return author;
    }

    public void setTime(User author) {
        this.author = author;
    }
}
