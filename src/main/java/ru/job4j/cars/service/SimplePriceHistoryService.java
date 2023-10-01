package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    @Override
    public Optional<PriceHistory> save(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    @Override
    public boolean deleteById(int id) {
        return priceHistoryRepository.deleteById(id);
    }

    @Override
    public void update(PriceHistory priceHistory) {
        priceHistoryRepository.update(priceHistory);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return priceHistoryRepository.findById(id);
    }

    @Override
    public Collection<PriceHistory> findAll() {
        return priceHistoryRepository.findAll();
    }
}
