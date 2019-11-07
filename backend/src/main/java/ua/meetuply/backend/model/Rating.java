package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private int value;
    private LocalDateTime date;
    private Integer ratedUser;
    private Integer ratedBy;
}
