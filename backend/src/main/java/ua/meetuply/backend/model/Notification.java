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

    private Integer notificationID;
    private LocalDateTime date_time;
    private int isRead;
    private Integer receiverID;
    private Integer templateID;

}
