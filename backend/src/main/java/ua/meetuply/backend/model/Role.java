package ua.meetuply.backend.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer roleId;
    private String roleName;
}