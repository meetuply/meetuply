package ua.meetuply.backend.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ban {
	
	private BanReason banReason;
	private AppUser author;
	private AppUser reported;
	private String description;
	private LocalDateTime time;

}
