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


    private Integer uid;
    private LocalDateTime dateTime;
    private Boolean isRead;
    private Integer receiverId;

    private String htmlText;
    private String plainText;

}
