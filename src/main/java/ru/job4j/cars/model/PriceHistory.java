package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private Integer id;
    private Integer before;
    private Integer after;
    @Include
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
}
