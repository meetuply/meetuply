package ua.meetuply.backend.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {

    private Integer notificationTemplateId;
    private String name;
    private String htmlText;
    private String plainText;
}