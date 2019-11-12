package ua.meetuply.backend.model;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StateImpl implements State{
    private Integer stateId;
    private String name;
}