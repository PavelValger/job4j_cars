package ru.job4j.cars.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostCreating {
    private String carName;
    private String bodywork;
    private String engineName;
    private String ownerName;
    private boolean status = false;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date historyStartAt;
    private LocalDateTime historyEndAt = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
    private String postDescription;
    private Integer priceHistoryAfter;
}
