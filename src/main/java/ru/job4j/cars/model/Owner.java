package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    @Include
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id")
    private History history;

    @Override
    public String toString() {
        return String.format("%s, %s", name, history);
    }
}
