package ua.meetuply.backend.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Integer notificationId;
    private LocalDateTime dateTime;
    private Boolean isRead;
    private Integer receiverId;
    private Integer templateId;

}
