package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {

    private Integer blogPostId;
    private String blogPostTitle;
    private String blogPostContent;
    private LocalDateTime time;
    private AppUser author;

    public BlogPost(String blogPostTitle, String blogPostContent, LocalDateTime time, AppUser author) {
        super();
        this.blogPostTitle=blogPostTitle;
        this.blogPostContent = blogPostContent;
        this.time = time;
        this.author = author;
    }
}