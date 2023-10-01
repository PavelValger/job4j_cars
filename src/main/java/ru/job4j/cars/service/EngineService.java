package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.Collection;
import java.util.Optional;

public interface EngineService {

    Optional<Engine> save(Engine engine);

    boolean deleteById(int id);

    void update(Engine engine);

    Optional<Engine> findById(int id);

    Collection<Engine> findAll();
}
