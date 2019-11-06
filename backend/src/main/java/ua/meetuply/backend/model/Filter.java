package ua.meetuply.backend.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

    private Integer id;
    private String name;
    private Double ratingFrom;
    private Double ratingTo;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Timestamp dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Timestamp dateTo;
    private Integer userId;
    private List<Integer> topicIds;

}
