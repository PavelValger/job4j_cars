package ru.job4j.cars.service;

import ru.job4j.cars.model.History;

import java.util.Collection;
import java.util.Optional;

public interface HistoryService {

    Optional<History> save(History history);

    boolean deleteById(int id);

    void update(History history);

    Optional<History> findById(int id);

    Collection<History> findAll();
}
