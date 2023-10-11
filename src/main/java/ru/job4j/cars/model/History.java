package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "history")
public class History {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Override
    public String toString() {
        return String.format("начало - %s, окончание - %s",
                FORMATTER.format(startAt), FORMATTER.format(endAt));
    }
}
