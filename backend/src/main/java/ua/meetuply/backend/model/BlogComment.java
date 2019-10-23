package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
public class BlogComment {

    private Integer blogCommentId;
    private String blogCommentContent;
    private LocalDateTime time;
    private BlogPost post;
    private AppUser author;

    public BlogComment(String blogCommentContent, LocalDateTime time, BlogPost post, AppUser author) {
        super();
        this.blogCommentContent=blogCommentContent;
        this.time = time;
        this.post=post;
        this.author = author;
    }
}