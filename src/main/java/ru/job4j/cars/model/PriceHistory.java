package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "price_history")
public class PriceHistory {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Integer id;
    private Integer before;
    private Integer after;
    @Include
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

    @Override
    public String toString() {
        return String.format("было - %s, стало - %s, дата изменения: %s",
                before, after, FORMATTER.format(created));
    }
}
