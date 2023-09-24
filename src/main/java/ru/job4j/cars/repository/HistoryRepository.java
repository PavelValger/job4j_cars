package ru.job4j.cars.repository;

import ru.job4j.cars.model.History;

import java.util.Collection;
import java.util.Optional;

public interface HistoryRepository {

    Optional<History> save(History history);

    boolean deleteById(int id);

    void update(History history);

    Optional<History> findById(int id);

    Collection<History> findAll();
}
