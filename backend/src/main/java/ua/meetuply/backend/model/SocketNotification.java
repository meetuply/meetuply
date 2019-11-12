package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SocketNotification {

    private Integer uid;
    private LocalDateTime dateTime;
    private Boolean isRead;
    private Integer receiverId;

    private String htmlText;
    private String plainText;

    public SocketNotification(Notification n,NotificationTemplate template) {
        this.uid = n.getNotificationId();
        this.dateTime = n.getDateTime();
        this.isRead = n.getIsRead();
        this.receiverId = n.getReceiverId();
        this.htmlText = template.getHtmlText();
        this.plainText = template.getPlainText();
    }
}
