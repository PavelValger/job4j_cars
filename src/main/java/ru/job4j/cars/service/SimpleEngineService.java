package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Optional<Engine> save(Engine engine) {
        return engineRepository.save(engine);
    }

    @Override
    public boolean deleteById(int id) {
        return engineRepository.deleteById(id);
    }

    @Override
    public void update(Engine engine) {
        engineRepository.update(engine);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public Collection<Engine> findAll() {
        return engineRepository.findAll();
    }
}
