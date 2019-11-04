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
    private Integer postId;
    private Integer authorId;
}