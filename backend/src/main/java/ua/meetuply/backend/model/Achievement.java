package ua.meetuply.backend.model;

import java.util.Objects;

public class Achievement {

    private Integer achievementId;
    private String title;
    private String description;
    private String icon;
    private Integer followersNumber;
    private Integer postsNumber;
    private Float rating;

    public Achievement() {
    }

    public Achievement(Integer achievementId, String title, String description,
                       String icon, Integer followersNumber, Integer postsNumber, Float rating) {
        this.achievementId = achievementId;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.followersNumber = followersNumber;
        this.postsNumber = postsNumber;
        this.rating = rating;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getFollowersNumber() {
        return followersNumber;
    }

    public void setFollowersNumber(Integer followersNumber) {
        this.followersNumber = followersNumber;
    }

    public Integer getPostsNumber() {
        return postsNumber;
    }

    public void setPostsNumber(Integer postsNumber) {
        this.postsNumber = postsNumber;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(getAchievementId(), that.getAchievementId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAchievementId());
    }
}
