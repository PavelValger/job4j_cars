package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.History;
import ru.job4j.cars.repository.HistoryRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleHistoryService implements HistoryService {
    private final HistoryRepository historyRepository;

    @Override
    public Optional<History> save(History history) {
        return historyRepository.save(history);
    }

    @Override
    public boolean deleteById(int id) {
        return historyRepository.deleteById(id);
    }

    @Override
    public void update(History history) {
        historyRepository.update(history);
    }

    @Override
    public Optional<History> findById(int id) {
        return historyRepository.findById(id);
    }

    @Override
    public Collection<History> findAll() {
        return historyRepository.findAll();
    }
}
