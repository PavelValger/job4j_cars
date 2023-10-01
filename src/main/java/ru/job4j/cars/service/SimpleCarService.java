package ru.job4j.cars.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CarRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public Optional<Car> save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public boolean deleteById(int id) {
        return carRepository.deleteById(id);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public Collection<Car> findAll() {
        return carRepository.findAll();
    }
}
