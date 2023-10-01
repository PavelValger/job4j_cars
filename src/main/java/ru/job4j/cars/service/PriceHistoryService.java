package ru.job4j.cars.service;

import ru.job4j.cars.model.PriceHistory;

import java.util.Collection;
import java.util.Optional;

public interface PriceHistoryService {

    Optional<PriceHistory> save(PriceHistory priceHistory);

    boolean deleteById(int id);

    void update(PriceHistory priceHistory);

    Optional<PriceHistory> findById(int id);

    Collection<PriceHistory> findAll();
}
