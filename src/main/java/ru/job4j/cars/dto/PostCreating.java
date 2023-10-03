package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCreating {
    private String carName;
    private String engineName;
    private String ownerName;
    private LocalDateTime historyStartAt;
    private LocalDateTime historyEndAt;
    private String postDescription;
    private Integer priceHistoryAfter;
}
