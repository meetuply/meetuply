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
    private Integer authorId;
}