package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private int value;
    private LocalDateTime date;
    private Integer ratedUser;
    private Integer ratedBy;
}
