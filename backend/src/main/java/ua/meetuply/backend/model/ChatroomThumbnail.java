package ua.meetuply.backend.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomThumbnail {

    private Message message;
    private AppUser user;
    private Integer roomId;

}
