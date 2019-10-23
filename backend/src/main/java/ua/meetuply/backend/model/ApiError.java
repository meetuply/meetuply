package ua.meetuply.backend.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
}
