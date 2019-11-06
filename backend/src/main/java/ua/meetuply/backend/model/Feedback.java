package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Integer feedbackId;
    private String feedbackContent;
    private LocalDateTime date;
    private Integer feedbackBy;
    private Integer feedbackTo;
}
