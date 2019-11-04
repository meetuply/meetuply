package ua.meetuply.backend.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
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
