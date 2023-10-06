package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreating {
    private String carName;
    private String engineName;
    private String ownerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyStartAt;
    private LocalDateTime historyEndAt = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private String postDescription;
    private Integer priceHistoryAfter;
}
