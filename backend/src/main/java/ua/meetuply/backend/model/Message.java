package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
public class Message {

    private Integer uid;
    private LocalDateTime date_time;
    private String content;
    private Integer from;
    private Integer to_user_id;//WTF
    private Integer to_room_id;

    public Message(Integer uid, LocalDateTime date_time, String content, Integer from, Integer to_user_id, Integer to_room_id) {
        this.uid = uid;
        this.date_time = date_time;
        this.content = content;
        this.from = from;
        this.to_user_id = to_user_id;
        this.to_room_id = to_room_id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }
}
