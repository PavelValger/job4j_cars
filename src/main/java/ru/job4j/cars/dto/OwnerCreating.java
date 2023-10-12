package ru.job4j.cars.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class OwnerCreating {
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endAt;
}
