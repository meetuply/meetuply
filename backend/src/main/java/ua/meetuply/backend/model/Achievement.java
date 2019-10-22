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
    private Integer followersNumber;
    private Integer postsNumber;
    private Float rating;
}
