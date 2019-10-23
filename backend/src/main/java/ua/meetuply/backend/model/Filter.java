package ua.meetuply.backend.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String name;
    private Double rating;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp dateTo;
    private Integer userId;

}
