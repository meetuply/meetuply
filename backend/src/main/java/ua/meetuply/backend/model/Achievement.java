package ua.meetuply.backend.model;

import lombok.*;

@Data
@AllArgsConstructor
public class Achievement {

    private Integer achievementId;
    private String title;
    private String description;
    private String icon;
    private Integer followers;
    private Integer posts;
    private Float rating;
    private Integer meetups;
}
